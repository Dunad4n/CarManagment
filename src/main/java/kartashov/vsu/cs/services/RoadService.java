package kartashov.vsu.cs.services;

import kartashov.vsu.cs.annotations.Component;
import kartashov.vsu.cs.annotations.DI;
import kartashov.vsu.cs.dao.CarDao;
import kartashov.vsu.cs.dao.RoadDao;
import kartashov.vsu.cs.models.Car;
import kartashov.vsu.cs.models.Road;
import kartashov.vsu.cs.utils.JsonParser;
import kartashov.vsu.cs.utils.PathParamsParser;
import lombok.Data;

import java.sql.SQLException;
import java.util.List;

@Data
@Component
public class RoadService implements Service<Road>{

    @DI
    private RoadDao roadDao;

    @DI
    private PathParamsParser paramsParser;

    @Override
    public Road get(String path) throws Exception {
        if (path != null && !path.isEmpty()) {
            return roadDao.get(paramsParser.getParams(path).get("id"));
        } else {
            throw new Exception("path is null or empty");
        }
    }

    @Override
    public List<Road> getAll() throws SQLException {
        return roadDao.getAll();
    }

    @Override
    public void save(String json) throws Exception {
        if (json != null && !json.isEmpty()) {
            Road road = JsonParser.getRoad(json);
            roadDao.create(road);
        } else {
            throw new Exception("json is null or empty");
        }
    }

    @Override
    public void update(String json, String params) throws Exception {
        if (json != null && !json.isEmpty() && params != null && !params.isEmpty()) {
            Long id = paramsParser.getParams(params).get("id");
            Road road = JsonParser.getRoad(json);
            roadDao.update(road, id);
        } else {
            throw new Exception("json is null or empty");
        }
    }

    @Override
    public void delete(String path) throws Exception {
        if (path != null && !path.isEmpty()) {
            roadDao.delete(paramsParser.getParams(path).get("id"));
        } else {
            throw new Exception("path is null or empty");
        }
    }
}
