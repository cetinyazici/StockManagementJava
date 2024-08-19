package com.stockmanagement.StockManagement.DataAccess.Concrete;

import com.stockmanagement.StockManagement.DataAccess.Abstract.IGenericRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public abstract class GenericRepository<T> implements IGenericRepository<T> {

    @PersistenceContext
    protected EntityManager manager;

    private final Class<T> entityClass;

    protected GenericRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void create(T entity) {
        manager.persist(entity);
    }

    @Override
    public void update(T entity) {
        manager.merge(entity);
    }

    @Override
    public void delete(int id) {
        T entity = manager.find(entityClass, id);
        if (entity != null) {
            manager.remove(entity);
        }
    }

    @Override
    public List<T> getAll() {
        return manager.createQuery("FROM " + entityClass.getName(), entityClass).getResultList();
    }

    @Override
    public T getById(int id) {
        return manager.find(entityClass, id);
    }
}
