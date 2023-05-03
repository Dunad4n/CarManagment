package kartashov.vsu.cs.dao;

import kartashov.vsu.cs.annotations.Component;
import kartashov.vsu.cs.annotations.DI;
import kartashov.vsu.cs.models.Road;
import kartashov.vsu.cs.utils.Mapper;
import lombok.Data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class RoadDao {


    private Connection connection;

    @DI
    private TrafficLaneDao trafficLaneDao;


    public Road get(Long id) throws SQLException {
        ResultSet rs;
        try {
            rs = connection.createStatement().executeQuery("SELECT * FROM road WHERE road_id = " + id);
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        Road road = null;
        while(rs.next()) {
            road = Mapper.toRoad(rs.getString(1));
            road.setTrafficLanes(trafficLaneDao.findAllByRoadId(id));
        }
        rs.close();
        return road;
    }

    public List<Road> getAll() throws SQLException {
        ResultSet rs;
        try {
            rs = connection.createStatement().executeQuery("SELECT * FROM road");
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        List<Road> roads = new ArrayList<>();
        while(rs.next()) {
            roads.add(Mapper.toRoad(rs.getString(1)));
            roads.get(roads.size() - 1).
                    setTrafficLanes(trafficLaneDao.findAllByRoadId(Long.parseLong(rs.getString(1))));
        }
        rs.close();
        return roads;
    }

    public void create() {
        try {
            connection.createStatement().execute("INSERT INTO road DEFAULT VALUES");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void delete(Long id) {
        try {
            connection.createStatement().executeUpdate("DELETE FROM road WHERE road_id = " + id);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
