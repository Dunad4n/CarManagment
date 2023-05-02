package kartashov.vsu.cs.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrafficLane {

    private Long id;
    private List<Car> cars;
    private Long roadId;

    public TrafficLane(Long id, Long roadId) {
        this.id = id;
        this.roadId = roadId;
    }

    @Override
    public String toString() {
        String[] carsId = new String[cars.size()];
        for (int i = 0; i < cars.size(); i++) {
            carsId[i] = cars.get(i).getId().toString();
        }
        return "TrafficLane{ id: "+ id + ", carsId: " + Arrays.toString(carsId) + " }";
    }

}
