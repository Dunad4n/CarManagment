package kartashov.vsu.cs.dao;

import kartashov.vsu.cs.annotations.Component;
import kartashov.vsu.cs.annotations.DI;
import kartashov.vsu.cs.models.TrafficLane;
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
public class TrafficLaneDao implements Dao<TrafficLane>{

    private Connection connection;

    @DI
    private CarDao carDao;

    public TrafficLaneDao() throws SQLException {
        connection = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USER, DatabaseInfo.PASSWORD);
    }

    @Override
    public TrafficLane get(Long id) throws SQLException {
        ResultSet rs;
        rs = connection.createStatement().executeQuery("SELECT * FROM lane WHERE lane_id = " + id);
        TrafficLane trafficLane = null;
        while(rs.next()) {
            trafficLane = Mapper.toTrafficLane(rs.getString(1), rs.getString(2));
            trafficLane.putCars(carDao.findAllByTrafficLaneId(id));
        }
        rs.close();
        return trafficLane;
    }

    @Override
    public List<TrafficLane> getAll() throws SQLException {
        ResultSet rs;
        try {
            rs = connection.createStatement().executeQuery("SELECT * FROM lane");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        List<TrafficLane> trafficLanes = new ArrayList<>();
        while(rs.next()) {
            trafficLanes.add(Mapper.toTrafficLane(rs.getString(1), rs.getString(2)));
            trafficLanes.get(trafficLanes.size() - 1).
                    putCars(carDao.findAllByTrafficLaneId(Long.parseLong(rs.getString(1))));
        }
        rs.close();
        return trafficLanes;
    }

    @Override
    public void create(TrafficLane item) throws SQLException {
        connection.createStatement().execute("INSERT INTO lane (road_id) VALUES (" + item.getRoadId().intValue() + ")");
    }

    @Override
    public void update(TrafficLane item, Long id) throws SQLException {
        connection.createStatement().executeUpdate("UPDATE lane SET road_id = " + item.getRoadId().intValue() +
                " WHERE lane_id = " + id.intValue());
    }

    @Override
    public void delete(Long id) throws SQLException {
        connection.createStatement().executeUpdate("DELETE FROM lane WHERE lane_id = " + id.intValue());
    }

    public List<TrafficLane> findAllByRoadId(Long roadId) throws SQLException {
        ResultSet rs;
        rs = connection.createStatement().executeQuery("SELECT * FROM lane WHERE road_id = " + roadId.intValue());
        List<TrafficLane> trafficLanes = new ArrayList<>();
        while (rs.next()) {
            trafficLanes.add(Mapper.toTrafficLane(rs.getString(1), rs.getString(2)));
            trafficLanes.get(trafficLanes.size() - 1).
                    putCars(carDao.findAllByTrafficLaneId(Long.parseLong(rs.getString(1))));
        }
        return trafficLanes;
    }
}
