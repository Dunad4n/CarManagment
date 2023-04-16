package kartashov.vsu.cs.dao;

import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public interface Dao<T>  {

    T get(Long id) throws Exception;
    Collection<T> getAll() throws IOException, CsvException;
    void save(T model) throws Exception;
    void update(Long id, T model) throws Exception;
    void delete(Long id) throws Exception;

}
