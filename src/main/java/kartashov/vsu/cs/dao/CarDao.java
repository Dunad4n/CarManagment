package kartashov.vsu.cs.dao;

import com.opencsv.CSVReader;
import kartashov.vsu.cs.annotations.Component;
import kartashov.vsu.cs.annotations.DI;
import kartashov.vsu.cs.models.Car;
import kartashov.vsu.cs.utils.Mapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileReader;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CarDao implements Dao<Car> {

    private final File file = new File("D:\\JavaTasks\\CarManagment\\src\\main\\java\\kartashov\\vsu\\cs\\data\\car.csv");

    @DI
    private Mapper mapper;

    @Override
    public Car get(Long id) throws Exception {
//        CSVReader reader = new CSVReader(new FileReader(file));
//        while(reader.peek() != null) {
//            String[] str = reader.readNext();
//            if(Long.parseLong(str[0]) == id) {
//                return new Car()
//            }
//        }
        return null;
    }

    @Override
    public Collection<Car> getAll() {
        return null;
    }

    @Override
    public void save(Car model) throws Exception {
    }

    @Override
    public void update(Long id, Car model) throws Exception {
    }

    @Override
    public void delete(Long id) {
    }
}