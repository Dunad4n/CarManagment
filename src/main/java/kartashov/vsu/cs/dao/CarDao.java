package kartashov.vsu.cs.dao;

import kartashov.vsu.cs.annotations.Component;
import kartashov.vsu.cs.models.Car;
import kartashov.vsu.cs.utils.DatabaseInfo;
import kartashov.vsu.cs.utils.Mapper;
import lombok.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class CarDao implements Dao<Car> {

    private Connection connection;

    public CarDao() throws SQLException {
        connection = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USER, DatabaseInfo.PASSWORD);
    }

    @Override
    public Car get(Long id) throws Exception {
        if (id == null) {
            throw new Exception("id is null");
        }
        ResultSet rs;
        rs = connection.createStatement().executeQuery("SELECT * FROM car WHERE car_id = " + id);
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

    @Override
    public List<Car> getAll() throws SQLException {
        ResultSet rs;
        rs =connection.createStatement().executeQuery("SELECT * FROM car");
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

    @Override
    public void create(Car item) throws SQLException {
        connection.createStatement().execute(
                "INSERT INTO car (speed, type, start_road_id, goal_road_id, lane_id)" +
                    " VALUES (" + item.getSpeed() + ", " +
                    "'" + item.getType().toString() + "'" +
                    ", " + item.getStartRoadId().intValue() +
                    ", " + item.getGoalRoadId().intValue() +
                    ", " + item.getLaneId().intValue() + ")"
        );
    }

    @Override
    public void update(Car item, Long id) throws SQLException {

        connection.createStatement().executeUpdate("UPDATE car SET speed = " + item.getSpeed() +
                                                       ", type = " + "'" + item.getType().toString() + "'" +
                                                       ", start_road_id = " + item.getStartRoadId().intValue() +
                                                       ", goal_road_id = " + item.getGoalRoadId().intValue() +
                                                       ", lane_id = " + item.getLaneId().intValue() +
                                                       " WHERE car_id = " + id.intValue());
    }

    @Override
    public void delete(Long id) throws SQLException {
        connection.createStatement().executeUpdate("DELETE FROM car WHERE car_id = " + id.intValue());
    }

    public List<Car> findAllByTrafficLaneId(Long laneId) throws SQLException {
        ResultSet rs;
        rs = connection.createStatement().executeQuery("SELECT * FROM car WHERE lane_id = " + laneId.intValue());
        List<Car> cars = new ArrayList<>();
        while(rs.next()) {
            cars.add(Mapper.toCar(rs.getString(1),
                                  rs.getString(2),
                                  rs.getString(3),
                                  rs.getString(4),
                                  rs.getString(5),
                                  rs.getString(6)));
        }
        return cars;
    }
}
