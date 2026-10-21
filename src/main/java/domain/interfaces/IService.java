package domain.interfaces;

import java.util.List;
import java.util.Optional;

public interface IService<T> {
    //Read All
    public List<T> findAll();

    //Read One
    public Optional<T> findOne(int id);

    //Update
    public T save(T t);

    //Delete
    public void delete(int id);
}
