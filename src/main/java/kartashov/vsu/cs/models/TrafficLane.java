package kartashov.vsu.cs.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrafficLane {

    private Long Id;
    private List<Long> cars;
    private Road road;

}
