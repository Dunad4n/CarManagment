package kartashov.vsu.cs.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Road {

    private Long id = null;
    private List<TrafficLane> trafficLanes = new ArrayList<>();

    public Road(List<TrafficLane> trafficLanes) {
        this.trafficLanes = trafficLanes;
    }

    public void addTrafficLane(TrafficLane trafficLane) {
        trafficLanes.add(trafficLane);
    }

    public Road(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < trafficLanes.size(); i++) {
            stringBuilder.append(trafficLanes.get(i).toString()).append(",");
        }
        String[] str = new String[trafficLanes.size()];
        for (int i = 0; i < trafficLanes.size(); i++) {
            str[i] = trafficLanes.get(i).getId().toString();
        }
        return "Road{" + id.toString() + ", Lanes: " + Arrays.toString(str) + "}";
    }

}
