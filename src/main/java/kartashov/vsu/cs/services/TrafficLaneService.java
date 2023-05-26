package kartashov.vsu.cs.services;

import kartashov.vsu.cs.annotations.Component;
import kartashov.vsu.cs.annotations.DI;
import kartashov.vsu.cs.dao.TrafficLaneDao;
import kartashov.vsu.cs.models.Car;
import kartashov.vsu.cs.models.TrafficLane;
import kartashov.vsu.cs.utils.JsonParser;
import kartashov.vsu.cs.utils.PathParamsParser;
import lombok.Data;

import java.sql.SQLException;
import java.util.List;

@Data
@Component
public class TrafficLaneService implements Service<TrafficLane> {

    @DI
    private TrafficLaneDao trafficLaneDao;

    @DI
    private PathParamsParser paramsParser;

    @Override
    public TrafficLane get(String path) throws Exception {
        if (path != null && !path.isEmpty()) {
            return trafficLaneDao.get(paramsParser.getParams(path).get("id"));
        } else {
            throw new Exception("path is null or empty");
        }
    }

    @Override
    public List<TrafficLane> getAll() throws SQLException {
        return trafficLaneDao.getAll();
    }

    @Override
    public void save(String json) throws Exception {
        if (json != null && !json.isEmpty()) {
            TrafficLane trafficLane = JsonParser.getTrafficLane(json);
            trafficLaneDao.create(trafficLane);
        } else {
            throw new Exception("json is null or empty");
        }
    }

    @Override
    public void update(String json, String params) throws Exception {
        if (json != null && !json.isEmpty() && params != null && !params.isEmpty()) {
            Long id = paramsParser.getParams(params).get("id");
            TrafficLane trafficLane = JsonParser.getTrafficLane(json);
            trafficLaneDao.update(trafficLane, id);
        } else {
            throw new Exception("json is null or empty");
        }
    }

    @Override
    public void delete(String path) throws Exception {
        if (path != null && !path.isEmpty()) {
            trafficLaneDao.delete(paramsParser.getParams(path).get("id"));
        } else {
            throw new Exception("path is null or empty");
        }
    }
}
