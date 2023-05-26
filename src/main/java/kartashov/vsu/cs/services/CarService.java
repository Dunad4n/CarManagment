package kartashov.vsu.cs.services;

import kartashov.vsu.cs.annotations.Component;
import kartashov.vsu.cs.annotations.DI;
import kartashov.vsu.cs.dao.CarDao;
import kartashov.vsu.cs.models.Car;
import kartashov.vsu.cs.utils.JsonParser;
import kartashov.vsu.cs.utils.PathParamsParser;
import lombok.Data;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Component
@Data
public class CarService implements Service<Car> {

    @DI
    private CarDao carDao;

    @DI
    private PathParamsParser paramsParser;

    @Override
    public Car get(String path) throws Exception {
        if (path != null && !path.isEmpty()) {
            return carDao.get(paramsParser.getParams(path).get("id"));
        } else {
            throw new Exception("path is null or empty");
        }
    }

    @Override
    public List<Car> getAll() throws SQLException {
        return carDao.getAll();
    }

    @Override
    public void save(String json) throws Exception {
        if (json != null && !json.isEmpty()) {
            Car car = JsonParser.getCar(json);
            carDao.create(car);
        } else {
            throw new Exception("json is null or empty");
        }
    }

    @Override
    public void update(String json, String params) throws Exception {
        if (json != null && !json.isEmpty() && params != null && !params.isEmpty()) {
            Long id = paramsParser.getParams(params).get("id");
            Car car = JsonParser.getCar(json);
            carDao.update(car, id);
        } else {
            throw new Exception("json is null or empty");
        }
    }

    @Override
    public void delete(String path) throws Exception {
        if (path != null && !path.isEmpty()) {
            carDao.delete(paramsParser.getParams(path).get("id"));
        } else {
            throw new Exception("path is null or empty");
        }
    }

}
