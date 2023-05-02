package kartashov.vsu.cs.dao.csv;

import kartashov.vsu.cs.models.Road;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {

    T get(Long id) throws SQLException;

    List<T> getAll() throws SQLException;

    void create(T item) throws SQLException;

    void update(T item) throws SQLException;

    void delete(Long id) throws SQLException;
}
