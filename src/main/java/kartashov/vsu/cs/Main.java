package kartashov.vsu.cs;

import kartashov.vsu.cs.annotations.DI;
import kartashov.vsu.cs.dao.CarDao;
import kartashov.vsu.cs.dao.RoadDao;
import kartashov.vsu.cs.dao.TrafficLaneDao;
import kartashov.vsu.cs.dao.csv.CarDaoCSV;
import kartashov.vsu.cs.dao.csv.RoadDaoCSV;
import kartashov.vsu.cs.dao.csv.TrafficLaneDaoCSV;
import kartashov.vsu.cs.models.Car;
import kartashov.vsu.cs.models.Road;
import kartashov.vsu.cs.models.TrafficLane;
import kartashov.vsu.cs.models.enums.CarType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    @DI
    private static CarDao carDao;

    @DI
    private static RoadDao roadDao;

    @DI
    private static TrafficLaneDao trafficLaneDao;

    final static private String URL = "jdbc:postgresql://localhost:9100/cars";
    final static private String USER = "root";
    final static private String PASSWORD = "root";

    static {
        try {
            ApplicationContext.injectDependencies();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection connection;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        roadDao.setConnection(connection);
        trafficLaneDao.setConnection(connection);
        carDao.setConnection(connection);
    }

    public static void main(String[] args) throws Exception {
        roadDao.create(null);
        List<Road> roads = roadDao.getAll();
        for (Road road : roads) {
            System.out.println(road.toString());
        }
        trafficLaneDao.create(new TrafficLane(null, 11L));
        trafficLaneDao.update(new TrafficLane(3L, 16L));
        carDao.create(new Car(100, CarType.PassengerCar, 11L, 16L, 4L));
        carDao.create(new Car(110, CarType.PassengerCar, 12L, 17L, 10L));
        carDao.update(new Car(2L, 210, CarType.Truck, 12L, 17L, 10L));
        carDao.delete(2L);
    }
}