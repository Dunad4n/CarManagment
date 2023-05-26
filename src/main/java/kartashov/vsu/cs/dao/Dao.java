package kartashov.vsu.cs.dao;

import kartashov.vsu.cs.models.Road;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {

    T get(Long id) throws Exception;

    List<T> getAll() throws Exception;

    void create(T item) throws Exception;

    void update(T item, Long id) throws Exception;

    void delete(Long id) throws Exception;
}
