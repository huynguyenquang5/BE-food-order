package com.example.be_food_order.controller.product;

import com.example.be_food_order.model.product.Image;
import com.example.be_food_order.model.product.Product;
import com.example.be_food_order.service.product.ImageService;
import com.example.be_food_order.service.product.ProductMethodService;
import com.example.be_food_order.service.product.ProductService;
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
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMethodService productMethodService;
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
    @GetMapping("/filters/{id}")
    public ResponseEntity<Iterable<Image>> findAllFilterStore(@PathVariable Long id){
        return new ResponseEntity<>(imageService.findAllFilterStore(id), HttpStatus.OK);
    }
    @GetMapping("/filters")
    public ResponseEntity<Iterable<Image>> findAllFilter(){
        return new ResponseEntity<>(imageService.findAllFilter(), HttpStatus.OK);
    }
    @DeleteMapping("/delete/product/{id}")
    public ResponseEntity<String> deleteAllByProduct(@PathVariable Long id){
        Optional<Product> product = productService.findOneById(id);
        if(product.isPresent()) {
            boolean checkImg = imageService.deleteAllByProduct(product.get().getId());
            boolean checkProduct = productService.deleteProduct(product.get().getId());
            boolean checkProductMethod = productMethodService.deleteProductMethod(product.get().getProductMethod().getId());
            if (checkImg && checkProduct && checkProductMethod){
                return new ResponseEntity<>("done",HttpStatus.OK);
            }else{
                return new ResponseEntity<>("error",HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>("error",HttpStatus.NOT_FOUND);
        }
    }
}
