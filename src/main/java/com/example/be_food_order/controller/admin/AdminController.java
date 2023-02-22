package com.example.be_food_order.controller.admin;

import com.example.be_food_order.model.product.Product;
import com.example.be_food_order.model.user.User;
import com.example.be_food_order.service.admin.AdminService;
import com.example.be_food_order.service.product.ProductService;
import com.example.be_food_order.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;


    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> findAllUser() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<User> findOneUser(@PathVariable Long id) {
        Optional<User> user = userService.findOneById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @PutMapping("/users/id={id}&status={status}")
    public ResponseEntity<User> activeBlockUser(@PathVariable Long id, @PathVariable Integer status) {
        Optional<User> user = userService.findOneById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        adminService.activeBlockUser(id, status);
        return new ResponseEntity<>(userService.save(user.get()), HttpStatus.OK);
    }

    @PutMapping("/users/id={id}&add_role_merchant")
    public ResponseEntity<User> addRoleMerchant(@PathVariable Long id) {
        Optional<User> user = userService.findOneById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        adminService.addRoleMerchant(id);
        return new ResponseEntity<>(userService.save(user.get()), HttpStatus.OK);
    }

    @PutMapping("/users/id={id}&add_role_merchant_partner")
    public ResponseEntity<User> addRoleMerchantPartner(@PathVariable Long id) {
        Optional<User> user = userService.findOneById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        adminService.addRoleMerchantPartner(id);
        return new ResponseEntity<>(userService.save(user.get()), HttpStatus.OK);
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<Iterable<Product>> findAllProductByCategory(@PathVariable Long id){
        return new ResponseEntity<>(productService.findAllByProductMethodCategoryId(id), HttpStatus.OK);
    }
}
