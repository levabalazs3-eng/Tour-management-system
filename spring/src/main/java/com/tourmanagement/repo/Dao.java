package com.tourmanagement.repo;

import com.tourmanagement.model.BaseEntity;

import java.util.Collection;
import java.util.Optional;

public interface Dao<T extends BaseEntity> {

    Collection<T> findAll();

    Optional<T> findById(long id);

    void create(T entity);

    void delete(Long id);

    void update(T entity);
}
