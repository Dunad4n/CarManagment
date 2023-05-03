package kartashov.vsu.cs.dao;

import kartashov.vsu.cs.annotations.Component;
import kartashov.vsu.cs.annotations.DI;
import kartashov.vsu.cs.models.TrafficLane;
import kartashov.vsu.cs.utils.Mapper;
import lombok.Data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class TrafficLaneDao {

    private Connection connection;

    @DI
    private CarDao carDao;

    public TrafficLane get(Long id) throws SQLException {
        ResultSet rs;
                try {
                    rs = connection.createStatement().executeQuery("SELECT * FROM lane WHERE lane_id = " + id);
                } catch (SQLException e) {
                    System.out.println(e);
                    return null;
                }
        TrafficLane trafficLane = null;
        while(rs.next()) {
            trafficLane = Mapper.toTrafficLane(rs.getString(1), rs.getString(2));
            trafficLane.setCars(carDao.findAllByTrafficLaneId(id));
        }
        rs.close();
        return trafficLane;
    }

    public List<TrafficLane> getAll() throws SQLException {
        ResultSet rs;
        try {
            rs = connection.createStatement().executeQuery("SELECT * FROM lane");
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        List<TrafficLane> trafficLanes = new ArrayList<>();
        while(rs.next()) {
            trafficLanes.add(Mapper.toTrafficLane(rs.getString(1), rs.getString(2)));
            trafficLanes.get(getAll().size() - 1).
                    setCars(carDao.findAllByTrafficLaneId(Long.parseLong(rs.getString(1))));
        }
        rs.close();
        return trafficLanes;
    }

    public void create(TrafficLane item) {
        try {
            connection.createStatement().execute("INSERT INTO lane (road_id) VALUES (" + item.getRoadId().intValue() + ")");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void update(TrafficLane item) {
        try {
            connection.createStatement().executeUpdate("UPDATE lane SET road_id = " + item.getRoadId().intValue() +
                    " WHERE lane_id = " + item.getId().intValue());
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void delete(Long id) {
        try {
            connection.createStatement().executeUpdate("DELETE FROM lane WHERE lane_id = " + id.intValue());
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<TrafficLane> findAllByRoadId(Long roadId) {
        ResultSet rs;
        try {
            rs = connection.createStatement().executeQuery("SELECT * FROM lane WHERE road_id = " + roadId.intValue());
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        List<TrafficLane> trafficLanes = new ArrayList<>();
        try {
            while (rs.next()) {
                trafficLanes.add(Mapper.toTrafficLane(rs.getString(1), rs.getString(2)));
                trafficLanes.get(trafficLanes.size() - 1).
                        setCars(carDao.findAllByTrafficLaneId(Long.parseLong(rs.getString(1))));
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return trafficLanes;
    }
}
