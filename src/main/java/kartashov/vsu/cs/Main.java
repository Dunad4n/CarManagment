package kartashov.vsu.cs;

import kartashov.vsu.cs.annotations.DI;
import kartashov.vsu.cs.dao.csv.CarDaoCSV;
import kartashov.vsu.cs.dao.csv.RoadDaoCSV;
import kartashov.vsu.cs.dao.csv.TrafficLaneDaoCSV;
import kartashov.vsu.cs.models.Car;
import kartashov.vsu.cs.models.Road;
import kartashov.vsu.cs.models.TrafficLane;
import kartashov.vsu.cs.models.enums.CarType;

import java.sql.Connection;
import java.sql.DriverManager;
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

    final static private String URL = "jdbc:postgresql://localhost:9100/cars";
    final static private String USER = "root";
    final static private String PASSWORD = "root";

    public static void main(String[] args) throws Exception {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }
}