package com.example.be_food_order.controller.product;

import com.example.be_food_order.model.product.Image;
import com.example.be_food_order.model.product.Product;
import com.example.be_food_order.model.product.ProductMethod;
import com.example.be_food_order.service.product.ImageService;
import com.example.be_food_order.service.product.ProductMethodService;
import com.example.be_food_order.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMethodService productMethodService;
    @Autowired
    private ImageService imageService;
    @PostMapping("create")
    public ResponseEntity<Image> createImage(@RequestBody Image image){
        productMethodService.save(image.getProduct().getProductMethod());
        ProductMethod productMethod = productMethodService.findLast();
        Product product = image.getProduct();
        product.setProductMethod(productMethod);
        productService.save(product);
        image.setProduct(product);
        return new ResponseEntity<>(imageService.save(image), HttpStatus.CREATED);
    }
    @GetMapping()
    public ResponseEntity<Iterable<Product>> findAllProduct(){

        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id){
        return new ResponseEntity<>(productService.findOneById(id).get(), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        productService.deleteById(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }
}
