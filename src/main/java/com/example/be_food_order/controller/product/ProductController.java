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
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<Iterable<Product>> findAllProduct() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findOne(@PathVariable Long id) {
        if (productService.findOneById(id).isPresent()) {
            return new ResponseEntity<>(productService.findOneById(id).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/store/{id}")
    public ResponseEntity<Iterable<Product>> findAllByStore(@PathVariable Long id) {
        if (productService.findAllByStore(id) != null) {
            return new ResponseEntity<>(productService.findAllByStore(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
