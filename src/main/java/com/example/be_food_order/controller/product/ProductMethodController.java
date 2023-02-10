package com.example.be_food_order.controller.product;

import com.example.be_food_order.model.product.Product;
import com.example.be_food_order.model.product.ProductMethod;
import com.example.be_food_order.service.product.ProductMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productMethods")
@CrossOrigin("*")
public class ProductMethodController {
    @Autowired
    private ProductMethodService productMethodService;
    @PostMapping("create")
    public ResponseEntity<ProductMethod> createProduct(@RequestBody ProductMethod productMethod){
        return new ResponseEntity<>(productMethodService.save(productMethod), HttpStatus.CREATED);
    }
    @GetMapping()
    public ResponseEntity<Iterable<ProductMethod>> findAllProduct(){
        return new ResponseEntity<>(productMethodService.findAll(), HttpStatus.OK);
    }
}
