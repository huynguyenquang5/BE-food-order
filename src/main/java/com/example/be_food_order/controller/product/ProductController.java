package com.example.be_food_order.controller.product;

import com.example.be_food_order.model.product.Product;
import com.example.be_food_order.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping("create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }
    @GetMapping()
    public ResponseEntity<Iterable<Product>> findAllProduct(){
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }
}
