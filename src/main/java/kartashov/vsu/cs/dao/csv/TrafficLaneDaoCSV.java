package kartashov.vsu.cs.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import kartashov.vsu.cs.annotations.Component;
import kartashov.vsu.cs.annotations.DI;
import kartashov.vsu.cs.models.Car;
import kartashov.vsu.cs.models.TrafficLane;
import kartashov.vsu.cs.utils.Mapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class TrafficLaneDaoCSV implements DaoCSV<TrafficLane> {

    private static final File file = new File("D:\\JavaTasks\\CarManagment\\src\\main\\java\\kartashov\\vsu\\cs\\data\\trafficLane.csv");
    private Long id = 1L;

    @DI
    private CarDaoCSV carDao;

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
    public TrafficLane get(Long id) throws Exception {
        checkId(id);
        CSVReader reader = new CSVReader(new FileReader(file));
        while(reader.peek() != null) {
            String[] str = reader.readNext();
            if(Long.parseLong(str[0]) == id) {
                List<Car> cars = new ArrayList<>();
                String[] carsId = str[1].split(";");
                for (int i = 0; i < carsId.length; i++) {
                    if(carsId[i].equals("empty")) {
                        continue;
                    }
                    cars.add(carDao.get(Long.parseLong(carsId[i])));
                }
                return new TrafficLane(Long.parseLong(str[0]), cars, Long.parseLong(str[2]));
            }
        }
        throw new Exception("There is no traffic lane with this id");
    }

    @Override
    public Collection<TrafficLane> getAll() throws Exception {
        CSVReader reader = new CSVReader(new FileReader(file));
        List<String[]> strings = reader.readAll();
        List<TrafficLane> trafficLanes = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            List<Car> cars = new ArrayList<>();
            String[] carsId = strings.get(i)[1].split(";");
            for (int j = 0; j < carsId.length; j++) {
                if(Objects.equals(carsId[j], "empty")) {
                    continue;
                }
                cars.add(carDao.get(Long.parseLong(carsId[j])));
            }
            trafficLanes.add(new TrafficLane(Long.parseLong(strings.get(i)[0]), cars, Long.parseLong(strings.get(i)[2])));
        }
        return trafficLanes;
    }

    @Override
    public void save(TrafficLane model) throws Exception {
        if(model.getId() != null) {
            throw new Exception("Id should be null before save");
        }
        model.setId(id++);
        CSVWriter writer = new CSVWriter(new FileWriter(file, true));
        writer.writeNext(mapper.trafficLaneToStringArray(model));
        writer.close();
    }

    @Override
    public void update(Long id, TrafficLane model) throws Exception {
        checkId(id);
        CSVReader reader = new CSVReader(new FileReader(file));
        List<String[]> trafficLanes = reader.readAll();
        reader.close();
        CSVWriter writer = new CSVWriter(new FileWriter(file));
        for (int i = 0; i < trafficLanes.size(); i++) {
            if(Long.parseLong(trafficLanes.get(i)[0]) == id) {
                model.setId(id);
                trafficLanes.set(i, mapper.trafficLaneToStringArray(model));
                writer.writeAll(trafficLanes);
                writer.close();
                return;
            }
        }
        writer.close();
        throw new Exception("There is no traffic lane with this id");
    }

    @Override
    public void delete(Long id) throws Exception {
        checkId(id);
        CSVReader reader = new CSVReader(new FileReader(file));
        List<String[]> trafficLanes = reader.readAll();
        reader.close();
        CSVWriter writer = new CSVWriter(new FileWriter(file));
        for (int i = 0; i < trafficLanes.size(); i++) {
            if(Long.parseLong(trafficLanes.get(i)[0]) == id) {
                String[] carsId = trafficLanes.get(i)[1].split(";");
                for(String carId: carsId) {
                    if(carId.equals("empty")) {
                        continue;
                    }
                    carDao.delete(Long.parseLong(carId));
                }
                trafficLanes.remove(i);
                writer.writeAll(trafficLanes);
                writer.close();
                return;
            }
        }
        writer.close();
        throw new Exception("There is no traffic lane with this id");
    }

    void checkId(Long id) throws Exception {
        if(id < 0) {
            throw new Exception("Invalid id");
        }
        if(id >= this.id) {
            throw new Exception("There is no traffic lane with this id");
        }
    }
}
