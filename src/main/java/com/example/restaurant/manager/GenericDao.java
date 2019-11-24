package com.example.restaurant.manager;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {

    /**
     * Persist the newInstance object into database
     */
    PK add(T newInstance);

    /**
     * Retrieve an object that was previously persisted to the database using
     * the indicated id as primary key
     */
    T get(PK id, boolean lock);

    /**
     * Retrieve object based on an example object
     */
    T getByExample(T exampleEntity);

    /**
     * Get a list of all objects from persistent storage in the database
     */
    List<T> getAll();

    /**
     * Get a list of all objects from persistent storage in the database (via
     */
    List<T> findAll();

    /**
     * Get a list of all objects based on an example object
     */
    List<T> getAllByExample(T exampleEntity);

    /**
     * Get a partial list of all objects from persistent storage in the database
     */
    List<T> getPartial(int offset, int limit);

    /**
     * Save changes made to a persistent object.
     */
    void update(T transientObject);

    /**
     * Remove an object from persistent storage in the database
     */
    void delete(T persistentObject);

    /**
     * Remove the total count of entities
     */
    public long getTotalCount();

    /**
     * Make entity persistent
     */
    public T makePersistent(T entity);

}