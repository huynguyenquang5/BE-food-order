package com.example.be_food_order.service.product;

import com.example.be_food_order.model.product.Category;
import com.example.be_food_order.repository.product.ICategoryRepository;
import com.example.be_food_order.service.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService implements ICRUDService<Category, Long> {
    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findOneById(Long aLong) {
        return categoryRepository.findById(aLong);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long aLong) {
        categoryRepository.deleteById(aLong);

    }
}
