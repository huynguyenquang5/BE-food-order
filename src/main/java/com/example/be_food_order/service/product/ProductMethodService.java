package com.example.be_food_order.service.product;

import com.example.be_food_order.model.product.ProductMethod;
import com.example.be_food_order.model.user.User;
import com.example.be_food_order.repository.product.IProductMethodRepository;
import com.example.be_food_order.service.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ProductMethodService implements ICRUDService<ProductMethod, Long> {
    @Autowired
    private IProductMethodRepository productMethodRepository;
    @Override
    public Iterable<ProductMethod> findAll() {
        return productMethodRepository.findAll();
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
    public Optional<User> findById(Long id) {
        return Optional.empty();
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
    public Optional<ProductMethod> findOneById(Long aLong) {
        return productMethodRepository.findById(aLong);
    }

    @Override
    public ProductMethod save(ProductMethod productMethod) {
        return productMethodRepository.save(productMethod);
    }

    @Override
    public void deleteById(Long aLong) {
        productMethodRepository.deleteById(aLong);

    }
}
