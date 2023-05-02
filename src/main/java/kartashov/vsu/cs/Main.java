package kartashov.vsu.cs;

import kartashov.vsu.cs.annotations.DI;
import kartashov.vsu.cs.dao.csv.CarDaoCSV;
import kartashov.vsu.cs.dao.csv.RoadDaoCSV;
import kartashov.vsu.cs.dao.csv.TrafficLaneDaoCSV;
import kartashov.vsu.cs.models.Car;
import kartashov.vsu.cs.models.Road;
import kartashov.vsu.cs.models.TrafficLane;
import kartashov.vsu.cs.models.enums.CarType;

import java.util.ArrayList;
import java.util.List;

public class Main {

    @DI
    private static CarDaoCSV carDao;

    @DI
    private static RoadDaoCSV roadDao;

    @DI
    private static TrafficLaneDaoCSV trafficLaneDao;

    static {
        try {
            ApplicationContext.injectDependencies();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
//        List<Long> trafficLanesId = new ArrayList<>();
//        trafficLanesId.add(2L);
//        trafficLanesId.add(10L);
//        trafficLanesId.add(7L);

//        roadDao.save(new Road(trafficLanesId));
//        roadDao.save(new Road(trafficLanesId));
//        roadDao.save(new Road(trafficLanesId));

//        System.out.println(roadDao.get(1L).toString());
//        System.out.println(roadDao.get(2L).toString());
//        roadDao.update(2L, new Road(new ArrayList<>()));
//        System.out.println(roadDao.get(2L));
//        roadDao.delete(3L);

        carDao.save(new Car(100, CarType.PassengerCar, 1L, 2L, 3L));
        carDao.save(new Car(50, CarType.Truck, 10L, 20L, 30L));
        carDao.save(new Car(20, CarType.Truck, 5L, 7L, 9L));

        List<Car> cars1 = (List<Car>) carDao.getAll();
        for (int i = 0; i < cars1.size(); i++) {
            System.out.println(cars1.get(i));
        }
        System.out.println(carDao.get(1L));

        carDao.update(1L, new Car(50, CarType.Truck, 10L, 20L, 30L));
        carDao.delete(1L);

        List<Car> cars2 = new ArrayList<>();
        cars2.add(carDao.get(2L));
        cars2.add(carDao.get(3L));

        trafficLaneDao.save(new TrafficLane(cars2, 1L));
        trafficLaneDao.save(new TrafficLane(new ArrayList<>(), 1L));
//        trafficLaneDao.delete(1L);
//        trafficLaneDao.update(2L, new TrafficLane(cars2, 1L));

        List<TrafficLane> trafficLanes = (List<TrafficLane>) trafficLaneDao.getAll();
        roadDao.save(new Road(trafficLanes));
        roadDao.delete(1L);
    }
}