package com.example.be_food_order.controller.product;

import com.example.be_food_order.model.product.Image;
import com.example.be_food_order.model.product.Product;
import com.example.be_food_order.service.product.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/images")
@CrossOrigin("*")
public class ImageController {
    @Autowired
    private ImageService imageService;
    @PostMapping("create")
    public ResponseEntity<Image> createProduct(@RequestBody Image image){
        return new ResponseEntity<>(imageService.save(image), HttpStatus.CREATED);
    }
    @GetMapping()
    public ResponseEntity<Iterable<Image>> findAllProduct(){
        return new ResponseEntity<>(imageService.findAll(), HttpStatus.OK);
    }
}
