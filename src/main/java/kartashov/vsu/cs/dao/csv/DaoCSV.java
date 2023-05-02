package kartashov.vsu.cs.dao.csv;

import java.util.Collection;

public interface DaoCSV<T>  {

    T get(Long id) throws Exception;
    Collection<T> getAll() throws Exception;
    void save(T model) throws Exception;
    void update(Long id, T model) throws Exception;
    void delete(Long id) throws Exception;

}
