package com.stockmanagement.StockManagement.DataAccess.Concrete;

import com.stockmanagement.StockManagement.DataAccess.Abstract.ICategoryRepository;
import com.stockmanagement.StockManagement.Entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository extends GenericRepository<Category> implements ICategoryRepository {
    public CategoryRepository() {
        super(Category.class);
    }
}
