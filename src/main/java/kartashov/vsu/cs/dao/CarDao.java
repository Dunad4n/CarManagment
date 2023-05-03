package kartashov.vsu.cs.dao;

import kartashov.vsu.cs.annotations.Component;
import kartashov.vsu.cs.models.Car;
import kartashov.vsu.cs.utils.Mapper;
import lombok.Data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class CarDao {

    private Connection connection;

    public Car get(Long id) throws SQLException {
        ResultSet rs;
        try {
            rs = connection.createStatement().executeQuery("SELECT * FROM car WHERE car_id = " + id);
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        Car car = null;
        while(rs.next()) {
            car = Mapper.toCar(rs.getString(1),
                               rs.getString(2),
                               rs.getString(3),
                               rs.getString(4),
                               rs.getString(5),
                               rs.getString(6));
        }
        rs.close();
        return car;
    }

    public List<Car> getAll() throws SQLException {
        ResultSet rs;
        try {
            rs =connection.createStatement().executeQuery("SELECT * FROM car");
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        List<Car> cars = new ArrayList<>();
        while(rs.next()) {
            cars.add(Mapper.toCar(rs.getString(1),
                                  rs.getString(2),
                                  rs.getString(3),
                                  rs.getString(4),
                                  rs.getString(5),
                                  rs.getString(6)));
        }
        rs.close();
        return cars;
    }

    public void create(Car item) {
        try {
            connection.createStatement().execute(
                    "INSERT INTO car (speed, type, start_road_id, goal_road_id, lane_id)" +
                        " VALUES (" + item.getSpeed() + ", " +
                        "'" + item.getType().toString() + "'" +
                        ", " + item.getStartRoadId().intValue() +
                        ", " + item.getGoalRoadId().intValue() +
                        ", " + item.getLaneId().intValue() + ")"
            );
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void update(Car item) {
        try {
            connection.createStatement().executeUpdate("UPDATE car SET speed = " + item.getSpeed() +
                                                           ", type = " + "'" + item.getType().toString() + "'" +
                                                           ", start_road_id = " + item.getStartRoadId().intValue() +
                                                           ", goal_road_id = " + item.getGoalRoadId().intValue() +
                                                           ", lane_id = " + item.getLaneId().intValue() +
                                                           " WHERE car_id = " + item.getId().intValue());
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void delete(Long id) {
        try {
            connection.createStatement().executeUpdate("DELETE FROM car WHERE car_id = " + id.intValue());
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<Car> findAllByTrafficLaneId(Long laneId) {
        ResultSet rs;
        try {
            rs = connection.createStatement().executeQuery("SELECT * FROM car WHERE lane_id = " + laneId.intValue());
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        List<Car> cars = new ArrayList<>();
        try {
            while(rs.next()) {
                cars.add(Mapper.toCar(rs.getString(1),
                                      rs.getString(2),
                                      rs.getString(3),
                                      rs.getString(4),
                                      rs.getString(5),
                                      rs.getString(6)));
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return cars;
    }
}
