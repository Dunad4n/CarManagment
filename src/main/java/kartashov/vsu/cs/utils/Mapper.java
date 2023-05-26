package kartashov.vsu.cs.utils;

import kartashov.vsu.cs.annotations.Component;
import kartashov.vsu.cs.models.Car;
import kartashov.vsu.cs.models.Road;
import kartashov.vsu.cs.models.TrafficLane;
import kartashov.vsu.cs.models.enums.CarType;

import java.util.ArrayList;
import java.util.List;

@Component
public class Mapper {


    public static Car toCar(String[] str) {
        return null;
    }

    public static Road toRoad(String roadId) {
        return new Road(Long.valueOf(roadId));
    }

    public static TrafficLane toTrafficLane(String laneId, String roadId) {
        return new TrafficLane(Long.parseLong(laneId), Long.parseLong(roadId));
    }

    public static Car toCar(String carId,
                            String speed,
                            String type,
                            String startGoalId,
                            String goalRoadId,
                            String laneId) {
        return new Car(Long.parseLong(carId),
                       Integer.parseInt(speed),
                       CarType.valueOf(type),
                       Long.parseLong(startGoalId),
                       Long.parseLong(goalRoadId),
                       Long.parseLong(laneId));
    }

    public Road createRoad(Long id, Long nextRoadId, Long prevRoadId, List<TrafficLane> trafficLanes) {
        return new Road(id, nextRoadId, prevRoadId, trafficLanes);
    }

    public String[] roadToStringArray(Road road) throws Exception {
        String[] str = new String[2];
        str[0] = road.getId().toString();
        str[1] = "";
        List<TrafficLane> trafficLanes = road.getTrafficLanes();
        if(road.getTrafficLanes() == null || road.getTrafficLanes().size() == 0) {
            str[1] = "empty";
        } else {
            for (int i = 0; i < trafficLanes.size(); i++) {
                if(i == trafficLanes.size() - 1) {
                    str[1] += trafficLanes.get(i).getId().toString();
                    break;
                }
                str[1] += trafficLanes.get(i).getId().toString() + ";";
            }
        }
        return str;
    }

    public String[] trafficLaneToStringArray(TrafficLane trafficLane) {
        String[] str = new String[3];
        str[0] = trafficLane.getId().toString();
        str[1] = "";
        List<Car> cars = trafficLane.getCars();
        if(cars == null || cars.size() == 0) {
            str[1] = "empty";
        } else {
            for (int i = 0; i < cars.size(); i++) {
                if(i == cars.size() - 1) {
                    str[1] += cars.get(i).getId().toString();
                    break;
                }
                str[1] += cars.get(i).getId().toString() + ";";
            }
        }
        str[2] = trafficLane.getRoadId().toString();
        return str;
    }

    public String[] carToStringArray(Car car) {
        String[] str = new String[6];
        str[0] = car.getId().toString();
        str[1] = Integer.toString(car.getSpeed());
        str[2] = car.getType().toString();
        str[3] = car.getStartRoadId().toString();
        str[4] = car.getGoalRoadId().toString();
        str[5] = car.getLaneId().toString();
        return str;
    }

    public Car stringArrayToCar(String[] str) {
        return new Car(Long.parseLong(str[0]),
                       Integer.parseInt(str[1]),
                       CarType.valueOf(str[2]),
                       Long.parseLong(str[3]),
                       Long.parseLong(str[4]),
                       Long.parseLong(str[5]));
    }

//    public TrafficLane stringArrayToTrafficLane(String[] str) {
//        String[] carsId = str[1].split(";");
//        if(Objects.equals(carsId[0], "empty")) {
//            return new TrafficLane(Long.parseLong(str[0]), new ArrayList<>(), Long.parseLong(str[2]));
//        }
//        List<Long> ids = new ArrayList<>();
//        for(String item: carsId) {
//            ids.add(Long.parseLong(item));
//        }
//        return new TrafficLane(Long.parseLong(str[0]), ids, Long.parseLong(str[2]));
//    }

    public String modelListToStringIds(List<Long> ids) {
        String str = "";
        for (int i = 0; i < ids.size(); i++) {
            if(i == ids.size() - 1) {
                str += ids.get(i);
                break;
            }
            str += ids.get(i).toString() + ";";
        }
        return str;
    }

}
