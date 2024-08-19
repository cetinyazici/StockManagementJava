package com.stockmanagement.StockManagement.Business.Concrete;

import com.stockmanagement.StockManagement.Business.Abstract.IGenericService;
import com.stockmanagement.StockManagement.DataAccess.Abstract.IGenericRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GenericManager <T> implements IGenericService<T> {
    private final IGenericRepository<T> genericRepository;

    @Autowired
    public GenericManager(IGenericRepository<T> genericRepository) {
        this.genericRepository = genericRepository;
    }

    @Override
    public void create(T entity) {
        genericRepository.create(entity);
    }

    @Override
    public void update(T entity) {
        genericRepository.update(entity);
    }

    @Override
    public void delete(int id) {
        T entity = genericRepository.getById(id);
        if (entity != null) {
            genericRepository.delete(id);
        }
    }

    @Override
    public List<T> getAll() {
        return genericRepository.getAll();
    }

    @Override
    public T getById(int id) {
        return genericRepository.getById(id);
    }
}
