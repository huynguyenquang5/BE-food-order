package com.example.be_food_order.service.admin;

import com.example.be_food_order.model.product.Product;
import com.example.be_food_order.model.store.Store;
import com.example.be_food_order.repository.product.IImageRepository;
import com.example.be_food_order.repository.product.IProductMethodRepository;
import com.example.be_food_order.repository.product.IProductRepository;
import com.example.be_food_order.repository.store.IStoreRepository;
import com.example.be_food_order.service.user.RoleService;
import com.example.be_food_order.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private IImageRepository imageRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IStoreRepository storeRepository;
    @Autowired
    private IProductMethodRepository productMethodRepository;

    public void activeBlockUser(Long id, Integer status) {
        if (userService.findOneById(id).isPresent()) {
            userService.findOneById(id).get().setStatus(status);
        }
        if (status == 0 && storeRepository.findStoreByUserId(id).isPresent()) {
            deleteAllProductByStoreId(storeRepository.findStoreByUserId(id).get().getId());
            deleteStoreByUserId(id);
        }
    }

    public void addRoleMerchant(Long id) {
        if (userService.findOneById(id).isPresent()) {
            userService.findOneById(id).get().setStatus(1);
            userService.findOneById(id).get().getRoles().remove(roleService.findByName("USER"));
            userService.findOneById(id).get().getRoles().add(roleService.findByName("MERCHANT"));
        }
    }

    public void addRoleMerchantPartner(Long id) {
        if (userService.findOneById(id).isPresent()) {
            userService.findOneById(id).get().setStatus(1);
            userService.findOneById(id).get().getRoles().remove(roleService.findByName("MERCHANT"));
            userService.findOneById(id).get().getRoles().add(roleService.findByName("PARTNER"));
        }
    }

    public void deleteStoreByUserId(Long id) {
        Optional<Store> store = storeRepository.findStoreByUserId(id);
        if (store.isPresent()) {
            store.get().setStatus(0);
        }
    }

    public void deleteAllProductByStoreId(Long id) {
        for (Product product : productRepository.findAllByStoreId(id)) {
            product.setStatus(0);
        }
    }
}
