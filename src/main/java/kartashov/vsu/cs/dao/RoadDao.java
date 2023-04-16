package kartashov.vsu.cs.dao;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import kartashov.vsu.cs.annotations.Component;
import kartashov.vsu.cs.annotations.DI;
import kartashov.vsu.cs.models.Road;
import kartashov.vsu.cs.utils.Mapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class RoadDao implements Dao<Road> {

    private static final File file = new File("D:\\JavaTasks\\CarManagment\\src\\main\\java\\kartashov\\vsu\\cs\\data\\road.csv");
    private Long id = 1L;

    @DI
    private TrafficLaneDao trafficLaneDao;
    @DI
    private Mapper mapper;

    static {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(file));
            writer.writeNext(new String[] {""});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Road get(Long id) throws Exception {
        checkId(id);
        CSVReader reader = new CSVReader(new FileReader(file));
        while(reader.peek() != null) {
            String[] str = reader.readNext();
            if(Long.parseLong(str[0]) == id) {
                return mapper.stringArrayToRoad(str);
            }
        }
        throw new Exception("There is no road with this id");
    }

    @Override
    public Collection<Road> getAll() throws IOException, CsvException {
        return new CSVReader(new FileReader(file)).readAll().stream().map(mapper::stringArrayToRoad).toList();
    }

    @Override
    public void save(Road model) throws Exception {
        if(model.getId() != null) {
            throw new Exception("Id should be null before save");
        }
        model.setId(id++);
        CSVWriter writer = new CSVWriter(new FileWriter(file, true));
        writer.writeNext(mapper.roadToStringArray(model));
        writer.close();
    }

    @Override
    public void update(Long id, Road model) throws Exception {
        checkId(id);
        CSVReader reader = new CSVReader(new FileReader(file));
        List<String[]> roads = reader.readAll();
        reader.close();
        CSVWriter writer = new CSVWriter(new FileWriter(file));
        for (int i = 0; i < roads.size(); i++) {
            if(Long.parseLong(roads.get(i)[0]) == id) {
                model.setId(id);
                roads.set(i, mapper.roadToStringArray(model));
                writer.writeAll(roads);
                writer.close();
                return;
            }
        }
        writer.close();
        throw new Exception("There is no road with this id");
    }

    @Override
    public void delete(Long id) throws Exception {
        checkId(id);
        CSVReader reader = new CSVReader(new FileReader(file));
        List<String[]> roads = reader.readAll();
        reader.close();
        CSVWriter writer = new CSVWriter(new FileWriter(file));
        for (int i = 0; i < roads.size(); i++) {
            if(Long.parseLong(roads.get(i)[0]) == id) {
                roads.remove(i);
                writer.writeAll(roads);
                writer.close();
                return;
            }
        }
        writer.close();
        throw new Exception("There is no road with this id");
    }

    void checkId(Long id) throws Exception {
        if(id < 0) {
            throw new Exception("Invalid id");
        }
        if(id >= this.id) {
            throw new Exception("There is no road with this id");
        }
    }
}
