package com.example.be_food_order.service.product;

import com.example.be_food_order.model.product.Product;
import com.example.be_food_order.repository.IStoreRepository;
import com.example.be_food_order.repository.product.IProductRepository;
import com.example.be_food_order.service.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ProductService implements ICRUDService<Product, Long> {
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IStoreRepository storeRepository;
    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findOneById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            return product;
        }else {
            return null;
        }
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long aLong) {
        productRepository.deleteById(aLong);
    }
    public Iterable<Product> findAllByStore(Long id){
        if(storeRepository.findById(id).isPresent()){
            return productRepository.findAllByStoreId(id);
        }else{
            return null;
        }
    }
}
