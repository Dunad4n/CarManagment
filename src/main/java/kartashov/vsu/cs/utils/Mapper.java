package kartashov.vsu.cs.utils;

import kartashov.vsu.cs.annotations.Component;
import kartashov.vsu.cs.models.Car;
import kartashov.vsu.cs.models.Road;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class Mapper {

    public static Car toCar(String[] str) {
        return null;
    }

    public Road stringArrayToRoad(String[] str) {
        String[] trafficLanesId = str[1].split(";");
        if(Objects.equals(trafficLanesId[0], "empty")) {
            return new Road(Long.parseLong(str[0]), new ArrayList<>());
        }
        List<Long> ids = new ArrayList<>();
        for(String item: trafficLanesId) {
            ids.add(Long.parseLong(item));
        }
        return new Road(Long.parseLong(str[0]), ids);
    }

    public String[] roadToStringArray(Road road) throws Exception {
        String[] str = new String[2];
        str[0] = road.getId().toString();
        str[1] = "";
        List<Long> trafficLanesId = road.getTrafficLanesId();
        if(road.getTrafficLanesId() == null || road.getTrafficLanesId().size() == 0) {
            str[1] = "empty";
        }
        for (int i = 0; i < trafficLanesId.size(); i++) {
            if(i == trafficLanesId.size() - 1) {
                str[1] += trafficLanesId.get(i);
                break;
            }
            str[1] += trafficLanesId.get(i).toString() + ";";
        }
        return str;
    }

}
