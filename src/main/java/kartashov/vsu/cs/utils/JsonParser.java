package kartashov.vsu.cs.utils;

import kartashov.vsu.cs.annotations.Component;
import kartashov.vsu.cs.models.Car;
import kartashov.vsu.cs.models.Road;
import kartashov.vsu.cs.models.TrafficLane;
import lombok.Data;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Data
@Component
public class JsonParser {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Car getCar(String json) throws IOException {
        return mapper.readValue(json, Car.class);
    }

    public static String serializeCar(Car car) throws IOException {
        return mapper.writeValueAsString(car);
    }

    public static String serializeCars(List<Car> cars) throws IOException {
        return mapper.writeValueAsString(cars);
    }

    public static TrafficLane getTrafficLane(String json) throws IOException {
        return mapper.readValue(json, TrafficLane.class);
    }

    public static String serializeTrafficLane(TrafficLane lane) throws IOException {
        return mapper.writeValueAsString(lane);
    }

    public static String serializeTrafficLanes(List<TrafficLane> lanes) throws IOException {
        return mapper.writeValueAsString(lanes);
    }

    public static Road getRoad(String json) throws IOException {
        return mapper.readValue(json, Road.class);
    }

    public static String serializeRoad(Road road) throws IOException {
        return mapper.writeValueAsString(road);
    }

    public static String serializeRoads(List<Road> roads) throws IOException {
        return mapper.writeValueAsString(roads);
    }
}
