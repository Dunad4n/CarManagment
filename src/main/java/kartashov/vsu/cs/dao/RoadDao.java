package kartashov.vsu.cs.dao;

import kartashov.vsu.cs.annotations.Component;
import kartashov.vsu.cs.annotations.DI;
import kartashov.vsu.cs.models.Road;
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
public class RoadDao implements Dao<Road>{


    private Connection connection;

    @DI
    private TrafficLaneDao trafficLaneDao;

    public RoadDao() throws SQLException {
        connection = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USER, DatabaseInfo.PASSWORD);
    }


    @Override
    public Road get(Long id) throws SQLException {
        ResultSet rs;
        try {
            rs = connection.createStatement().executeQuery("SELECT * FROM road WHERE road_id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        Road road = null;
        while(rs.next()) {
            road = Mapper.toRoad(rs.getString(1));
            road.putTrafficLanes(trafficLaneDao.findAllByRoadId(id));
        }
        rs.close();
        return road;
    }

    @Override
    public List<Road> getAll() throws SQLException {
        ResultSet rs;
        rs = connection.createStatement().executeQuery("SELECT * FROM road");
        List<Road> roads = new ArrayList<>();
        while(rs.next()) {
            roads.add(Mapper.toRoad(rs.getString(1)));
            roads.get(roads.size() - 1).
                    putTrafficLanes(trafficLaneDao.findAllByRoadId(Long.parseLong(rs.getString(1))));
        }
        rs.close();
        return roads;
    }

    @Override
    public void create(Road road) throws SQLException {
        connection.createStatement().execute("INSERT INTO road (next_road_id, prev_road_id) " +
                                                 "VALUES (" + road.getNextRoadId().intValue() + ", " + road.getPrevRoadId().intValue() + ")" );
    }

    @Override
    public void update(Road road, Long id) throws SQLException {
        connection.createStatement().execute("UPDATE road SET next_road_id = " + road.getNextRoadId().intValue() +
                                                ", prev_road_id = " + road.getPrevRoadId().intValue() +
                                                " WHERE road_id = " + id.intValue());
    }

    @Override
    public void delete(Long id) throws SQLException {
        connection.createStatement().executeUpdate("DELETE FROM road WHERE road_id = " + id);
    }
}
