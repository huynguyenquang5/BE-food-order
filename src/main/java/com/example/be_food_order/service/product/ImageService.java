package com.example.be_food_order.service.product;

import com.example.be_food_order.model.product.Image;
import com.example.be_food_order.model.product.Product;
import com.example.be_food_order.repository.cart.IDeliveryRepository;
import com.example.be_food_order.repository.product.IImageRepository;
import com.example.be_food_order.repository.product.IProductMethodRepository;
import com.example.be_food_order.repository.product.IProductRepository;
import com.example.be_food_order.service.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService implements ICRUDService<Image, Long> {
    @Autowired
    private IImageRepository imageRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IProductMethodRepository productMethodRepository;
    @Autowired
    private IDeliveryRepository iDeliveryRepository;

    @Override
    public Iterable<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Optional<Image> findOneById(Long aLong) {
        return imageRepository.findById(aLong);
    }

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public void deleteById(Long aLong) {
        imageRepository.deleteById(aLong);
    }
    public Iterable<Image> findAllByProduct(Long productId) {
        if (productRepository.findById(productId).isPresent()){
            return imageRepository.findAllByProductId(productId);
        }else{
            return null;
        }
    }
    public Iterable<Image> findAllFilterStore(Long id) {
        return imageRepository.findAllFilterStore(id);
    }
    public Iterable<Image> findAllFilter() {
        return imageRepository.findAllFilter();
    }
    @Transactional
    public boolean deleteAllByProduct(Product product) {
        try {
            imageRepository.deleteListImageByProduct(product.getId());
            productRepository.deleteById(product.getId());
            productMethodRepository.deleteById(product.getProductMethod().getId());
            return true;
        }catch (Exception e) {
            return false;
        }
    }
    public Iterable<Image> findAllByCategoryId(Long id){
        return imageRepository.findAllByCategoryId(id);
    }
    public Iterable<Image> findAllByProductName(String name){
        return imageRepository.findAllByProductNameContains(name);
    }
    public Iterable<Image> findAllTopFood(){
        return imageRepository.findAllTopFood();
    }

}
