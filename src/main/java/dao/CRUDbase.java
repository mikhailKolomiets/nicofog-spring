package dao;

import java.util.List;

/**
 * Created by mihail on 11/9/18.
 */
public interface CRUDbase<T> {

    T add(T t);

    T getById(long id);

    T update(T t);

    List<T> getAll();

    boolean deleteById(long id);

    default String getThrowableMessage() {
        return "No message implementation";
    }

    default T getByName(String name) {
        return null;
    }

}