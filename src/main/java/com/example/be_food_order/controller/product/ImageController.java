package com.example.be_food_order.controller.product;

import com.example.be_food_order.model.product.Image;
import com.example.be_food_order.model.product.Product;
import com.example.be_food_order.service.product.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @GetMapping("/product/{id}")
    public ResponseEntity<Iterable<Image>> findAllByProduct(@PathVariable Long id){
        if (imageService.findAllByProduct(id) != null){
            return new ResponseEntity<>(imageService.findAllByProduct(id), HttpStatus.OK);
        }else {
            List<Image> images = new ArrayList<>();
            return new ResponseEntity<>(images,HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Image> updateImage(@PathVariable Long id, @RequestBody Image image){
       Optional<Image> image1 = imageService.findOneById(id);
        if(image1.isPresent()){
            return new ResponseEntity<>(imageService.save(image), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        imageService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
