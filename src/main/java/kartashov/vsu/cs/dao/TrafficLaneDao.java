package kartashov.vsu.cs.dao;

import kartashov.vsu.cs.annotations.Component;
import kartashov.vsu.cs.dao.csv.Dao;
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
public class TrafficLaneDao implements Dao<TrafficLane> {

    private Connection connection;

    @Override
    public TrafficLane get(Long id) throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM lane WHERE lane_id = " + id);
        TrafficLane trafficLane = null;
        while(rs.next()) {
            trafficLane = Mapper.toTrafficLane(rs.getString(1), rs.getString(2));
        }
        rs.close();
        return trafficLane;
    }

    @Override
    public List<TrafficLane> getAll() throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM lane");
        List<TrafficLane> trafficLanes = new ArrayList<>();
        while(rs.next()) {
            trafficLanes.add(Mapper.toTrafficLane(rs.getString(1), rs.getString(2)));
        }
        rs.close();
        return trafficLanes;
    }

    @Override
    public void create(TrafficLane item) throws SQLException {
        connection.createStatement().execute("INSERT INTO lane (road_id) VALUES (" + item.getRoadId().intValue() + ")");
    }

    @Override
    public void update(TrafficLane item) throws SQLException {
        connection.createStatement().executeUpdate("UPDATE lane SET road_id = " + item.getRoadId().intValue() +
                                                       " WHERE lane_id = " + item.getId().intValue());
    }

    @Override
    public void delete(Long id) throws SQLException {
        connection.createStatement().executeUpdate("DELETE FROM lane WHERE lane_id = " + id.intValue());
    }
}
