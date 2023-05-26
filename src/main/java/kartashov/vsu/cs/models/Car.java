package kartashov.vsu.cs.models;

import kartashov.vsu.cs.models.enums.CarType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Getter
    private Long id = null;

    @Setter
    @Getter
    private int speed;

    @Setter
    @Getter
    private CarType type;

    @Setter
    @Getter
    private Long startRoadId;

    @Setter
    @Getter
    private Long goalRoadId;

    @Setter
    @Getter
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
