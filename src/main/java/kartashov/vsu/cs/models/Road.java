package kartashov.vsu.cs.models;

import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Road {

    @Getter
    private Long id = null;

    @Getter
    @Setter
    private Long nextRoadId;

    @Getter
    @Setter
    private Long prevRoadId;

    @Getter
    private List<TrafficLane> trafficLanes = new ArrayList<>();

    public Road(List<TrafficLane> trafficLanes) {
        this.trafficLanes = trafficLanes;
    }

    public Road(Long nextRoadId, Long prevRoadId) {
        this.nextRoadId = nextRoadId;
        this.prevRoadId = prevRoadId;
    }

    public void putTrafficLanes(List<TrafficLane> trafficLanes) {
        this.trafficLanes = trafficLanes;
    }
    public Road(Long id) {
        this.id = id;
    }


}
