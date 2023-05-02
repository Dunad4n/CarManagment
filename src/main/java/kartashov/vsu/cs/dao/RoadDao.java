package kartashov.vsu.cs.dao;

import kartashov.vsu.cs.annotations.Component;
import kartashov.vsu.cs.dao.csv.Dao;
import kartashov.vsu.cs.dao.csv.DaoCSV;
import kartashov.vsu.cs.models.Road;
import kartashov.vsu.cs.utils.Mapper;
import lombok.Data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Component
public class RoadDao implements Dao<Road> {


    private Connection connection;


    @Override
    public Road get(Long id) throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM road WHERE road_id = " + id);
        int columns = rs.getMetaData().getColumnCount();
        Road road = null;
        while(rs.next()) {
            for (int i = 1; i <= columns; i++) {
                road = Mapper.toRoad(rs.getString(i));
            }
        }
        rs.close();
        return road;
    }

    @Override
    public List<Road> getAll() throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM road");
        int columns = rs.getMetaData().getColumnCount();
        List<Road> roads = new ArrayList<>();
        while(rs.next()) {
            for (int i = 1; i <= columns; i++) {
                roads.add(Mapper.toRoad(rs.getString(i)));
            }
        }
        rs.close();
        return roads;
    }

    @Override
    public void create(Road road) throws SQLException {
        connection.createStatement().execute("INSERT INTO road DEFAULT VALUES");
    }

    @Override
    public void update(Road road) throws SQLException {}

    @Override
    public void delete(Long id) throws SQLException {
        connection.createStatement().executeUpdate("DELETE FROM road WHERE road_id = " + id);
    }
}
