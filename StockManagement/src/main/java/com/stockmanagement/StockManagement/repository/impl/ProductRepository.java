package com.stockmanagement.StockManagement.repository.impl;

import com.stockmanagement.StockManagement.model.Product;
import com.stockmanagement.StockManagement.repository.IProductRepository;
import com.stockmanagement.StockManagement.service.IProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository extends GenericRepository<Product> implements IProductRepository {
    @PersistenceContext
    protected EntityManager manager;

    protected ProductRepository() {
        super(Product.class);
    }

    @Override
    public long getTotalStock() {
        String hql = "SELECT SUM(p.stock_quantity) FROM Product p";
        TypedQuery<Long> query = manager.createQuery(hql, Long.class);
        Long result = query.getSingleResult();
        return result != null ? result : 0;
    }

    @Override
    public List<Product> findByStockQuantityLessThanEqual(int threshold) {
        String jpql = "SELECT p FROM Product p WHERE p.stock_quantity <= :threshold";
        TypedQuery<Product> query = manager.createQuery(jpql, Product.class);
        query.setParameter("threshold", threshold);
        return query.getResultList();
    }


}
