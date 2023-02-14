package com.example.be_food_order.service.product;

import com.example.be_food_order.model.product.Category;
import com.example.be_food_order.model.user.User;
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
    public User findByUserName(String userName) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public boolean checkUser(User user) {
        return false;
    }

    @Override
    public boolean isRegister(User user) {
        return false;
    }

    @Override
    public Optional<Category> findOneById(Long aLong) {
        return categoryRepository.findById(aLong);
    }
33
    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long aLong) {
        categoryRepository.deleteById(aLong);

    }
}
