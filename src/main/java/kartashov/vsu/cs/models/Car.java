package kartashov.vsu.cs.models;

import kartashov.vsu.cs.models.enums.CarType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    private Long id = null;
    private int speed;
    private CarType type;
    private Long startRoadId;
    private Long goalRoadId;
    private Long laneId;

    public Car(int speed, CarType type, Long startRoadId, Long goalRoadId, Long laneId) {
        this.speed = speed;
        this.type = type;
        this.startRoadId = startRoadId;
        this.goalRoadId = goalRoadId;
        this.laneId = laneId;
    }

    @Override
    public String toString() {
        return "Car{ id: " + id + ", speed: " + speed + ", type: " + type.toString() + ", startRoadId: "
                + startRoadId + ", goalRoadId: " + goalRoadId + ", laneId: " + laneId + " }";
    }
}
