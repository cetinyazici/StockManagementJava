package com.stockmanagement.StockManagement.service;

import java.util.List;

public interface IGenericService<T> {
    void create(T entity);

    void update(T entity);

    void delete(int id);

    List<T> getAll();

    T getById(int id);
}
