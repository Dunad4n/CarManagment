package kartashov.vsu.cs.dao;

import kartashov.vsu.cs.annotations.Component;
import kartashov.vsu.cs.annotations.DI;
import kartashov.vsu.cs.models.TrafficLane;
import kartashov.vsu.cs.utils.Mapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class TrafficLaneDao implements Dao<TrafficLane>{

    private final File file = new File("D:\\JavaTasks\\CarManagment\\src\\main\\java\\kartashov\\vsu\\cs\\data\\trafficLane.csv");

    @DI
    private Mapper mapper;

    @Override
    public TrafficLane get(Long id) throws Exception {
        return null;
    }

    @Override
    public Collection<TrafficLane> getAll() {
        return null;
    }

    @Override
    public void save(TrafficLane model) throws Exception {
    }

    @Override
    public void update(Long id, TrafficLane model) throws Exception {
    }

    @Override
    public void delete(Long id) {
    }
}
