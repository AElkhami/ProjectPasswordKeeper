package com.elkhamitech.projectkeeper.data.roomdatabase.crud;

import java.util.List;

/**
 * Created by A.Elkhami on 08,April,2019
 */
public interface LocalDbRepository<T, E> {

    long create(T String);

    List<T> getListDb(E id);
    T readFromDb(E id);

    void update(T entity);
    void delete(T entity);
}
