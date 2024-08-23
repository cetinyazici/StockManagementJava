package com.stockmanagement.StockManagement.repository;

import java.util.List;

public interface IGenericRepository<T> {
    void create(T entity);

    void update(T entity);

    void delete(int id);

    List<T> getAll();

    T getById(int id);

    long count();
}
