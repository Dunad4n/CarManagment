package kartashov.vsu.cs.models;

import kartashov.vsu.cs.models.enums.CarType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    private Long Id;
    private int speed;
    private CarType type;
    private Road startRoad;
    private Road goalRoad;
    private TrafficLane lane;



}
