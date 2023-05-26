package kartashov.vsu.cs.models;

import lombok.*;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class TrafficLane {

    @Getter
    private Long id = null;

    @Getter
    private List<Car> cars;

    @Setter
    @Getter
    private Long roadId;

    public TrafficLane(Long id, Long roadId) {
        this.id = id;
        this.roadId = roadId;
    }

    public TrafficLane(Long roadId) {
        this.roadId = roadId;
    }

    public void putCars(List<Car> cars) {
        this.cars = cars;
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
