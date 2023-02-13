package com.example.be_food_order.controller.product;

import com.example.be_food_order.model.product.Category;
import com.example.be_food_order.model.product.Product;
import com.example.be_food_order.service.product.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public ResponseEntity<Iterable<Category>> findAll(){
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }
    @PostMapping("create")
    public ResponseEntity<Category> createProduct(@RequestBody Category category){
        return new ResponseEntity<>(categoryService.save(category), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id){
        return new ResponseEntity<>(categoryService.findOneById(id).get(), HttpStatus.OK);
    }
}
