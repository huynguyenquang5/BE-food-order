package com.example.be_food_order.service.product;

import com.example.be_food_order.model.product.Product;
import com.example.be_food_order.repository.product.IProductRepository;
import com.example.be_food_order.service.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ProductService implements ICRUDService<Product, Long> {
    @Autowired
    private IProductRepository productRepository;
    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findOneById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long aLong) {
        productRepository.deleteById(aLong);
    }
    public Product findLast(){
       return productRepository.findLast();
    }
}
