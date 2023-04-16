package kartashov.vsu.cs.models;

import kartashov.vsu.cs.dao.TrafficLaneDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Road {

    private Long id = null;
    private List<Long> trafficLanesId;

    public Road(List<Long> trafficLanesId) {
        this.trafficLanesId = trafficLanesId;
    }

    public void addTrafficLane(TrafficLane trafficLane) {
        trafficLanesId.add(trafficLane.getId());
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < trafficLanesId.size(); i++) {
            str.append(trafficLanesId.get(i).toString()).append(",");
        }
        return "Road{" + id.toString() + ", Lanes: " + trafficLanesId + "}";
    }

}
