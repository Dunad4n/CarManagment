package kartashov.vsu.cs.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface Service<T> {

    T get(String json) throws Exception;

    List<T> getAll() throws SQLException;

    void save(String json) throws Exception;

    void update(String json, String params) throws Exception;

    void delete(String json) throws Exception;

}
