package kartashov.vsu.cs.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import kartashov.vsu.cs.annotations.Component;
import kartashov.vsu.cs.annotations.DI;
import kartashov.vsu.cs.dao.Dao;
import kartashov.vsu.cs.models.Car;
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
public class CarDaoCSV implements Dao<Car> {

    private static final File file = new File("D:\\JavaTasks\\CarManagment\\src\\main\\java\\kartashov\\vsu\\cs\\data\\car.csv");
    private Long id = 1L;


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
    public Car get(Long id) throws Exception {
        checkId(id);
        CSVReader reader = new CSVReader(new FileReader(file));
        while(reader.peek() != null) {
            String[] str = reader.readNext();
            if(Long.parseLong(str[0]) == id) {
                return mapper.stringArrayToCar(str);
            }
        }
        throw new Exception("There is no car with this id");
    }

    @Override
    public Collection<Car> getAll() throws IOException, CsvException {
        CSVReader reader = new CSVReader(new FileReader(file));
        List<Car> cars = new ArrayList<>();
        List<String[]> strings = reader.readAll();
        for (int i = 0; i < strings.size(); i++) {
            cars.add(mapper.stringArrayToCar(strings.get(i)));
        }
        return cars;
    }

    @Override
    public void save(Car model) throws Exception {
        if(model.getId() != null) {
            throw new Exception("Id should be null before save");
        }
        model.setId(id++);
        CSVWriter writer = new CSVWriter(new FileWriter(file, true));
        writer.writeNext(mapper.carToStringArray(model));
        writer.close();
    }

    @Override
    public void update(Long id, Car model) throws Exception {
        checkId(id);
        CSVReader reader = new CSVReader(new FileReader(file));
        List<String[]> cars = reader.readAll();
        reader.close();
        CSVWriter writer = new CSVWriter(new FileWriter(file));
        for (int i = 0; i < cars.size(); i++) {
            if(Long.parseLong(cars.get(i)[0]) == id) {
                model.setId(id);
                cars.set(i, mapper.carToStringArray(model));
                writer.writeAll(cars);
                writer.close();
                return;
            }
        }
        writer.close();
        throw new Exception("There is no car with this id");
    }

    @Override
    public void delete(Long id) throws Exception {
        checkId(id);
        CSVReader reader = new CSVReader(new FileReader(file));
        List<String[]> cars = reader.readAll();
        reader.close();
        CSVWriter writer = new CSVWriter(new FileWriter(file));
        for (int i = 0; i < cars.size(); i++) {
            if(Long.parseLong(cars.get(i)[0]) == id) {
                cars.remove(i);
                writer.writeAll(cars);
                writer.close();
                return;
            }
        }
        writer.close();
        throw new Exception("There is no car with this id");
    }

    void checkId(Long id) throws Exception {
        if(id < 0) {
            throw new Exception("Invalid id");
        }
        if(id >= this.id) {
            throw new Exception("There is no car with this id");
        }
    }
}