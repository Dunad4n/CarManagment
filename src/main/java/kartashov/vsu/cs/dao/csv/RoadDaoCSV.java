package kartashov.vsu.cs.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import kartashov.vsu.cs.annotations.Component;
import kartashov.vsu.cs.annotations.DI;
import kartashov.vsu.cs.dao.Dao;
import kartashov.vsu.cs.models.Road;
import kartashov.vsu.cs.models.TrafficLane;
import kartashov.vsu.cs.utils.Mapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class RoadDaoCSV implements Dao<Road> {

    private static final File file = new File("D:\\JavaTasks\\CarManagment\\src\\main\\java\\kartashov\\vsu\\cs\\data\\road.csv");
    private Long id = 1L;

    @DI
    private TrafficLaneDaoCSV trafficLaneDao;
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
                List<TrafficLane> trafficLanes = new ArrayList<>();
                String[] trafficLanesId = str[1].split(";");
                for (int i = 0; i < trafficLanesId.length; i++) {
                    if(trafficLanesId[i].equals("empty")) {
                        continue;
                    }
                    trafficLanes.add(trafficLaneDao.get(Long.parseLong(trafficLanesId[i])));
                }
                return new Road(Long.parseLong(str[0]), trafficLanes);
            }
        }
        throw new Exception("There is no road with this id");
    }

    @Override
    public Collection<Road> getAll() throws Exception {
        CSVReader reader = new CSVReader(new FileReader(file));
        List<String[]> strings = reader.readAll();
        List<Road> roads = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            List<TrafficLane> trafficLanes = new ArrayList<>();
            String[] trafficLanesId = strings.get(i)[1].split(";");
            for (int j = 0; j < trafficLanesId.length; j++) {
                if(trafficLanesId[j].equals("empty")) {
                    continue;
                }
                trafficLanes.add(trafficLaneDao.get(Long.parseLong(trafficLanesId[j])));
            }
            roads.add(new Road(Long.parseLong(strings.get(i)[0]), trafficLanes));
        }
        return roads;
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
                String[] trafficLanesId = roads.get(i)[1].split(";");
                for(String trafficLaneId: trafficLanesId) {
                    if(trafficLaneId.equals("empty")) {
                        continue;
                    }
                    trafficLaneDao.delete(Long.parseLong(trafficLaneId));
                }
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
