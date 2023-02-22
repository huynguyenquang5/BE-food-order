package com.example.be_food_order.controller.product;

import com.example.be_food_order.model.message.Message;
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

    @PostMapping("/create")
    public ResponseEntity<Image> createImage(@RequestBody Image image) {
        productMethodService.save(image.getProduct().getProductMethod());
        ProductMethod productMethod = productMethodService.findLast();
        Product product = image.getProduct();
        product.setProductMethod(productMethod);
        productService.save(product);
        product = productService.findLast();
        image.setProduct(product);
        return new ResponseEntity<>(imageService.save(image), HttpStatus.CREATED);
    }

    @PostMapping("/add")
    public ResponseEntity<Image> addImage(@RequestBody Image image) {
        return new ResponseEntity<>(imageService.save(image), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<Iterable<Image>> findAllImage() {
        return new ResponseEntity<>(imageService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Iterable<Image>> findAllByProduct(@PathVariable Long id) {
        if (imageService.findAllByProduct(id) != null) {
            return new ResponseEntity<>(imageService.findAllByProduct(id), HttpStatus.OK);
        } else {
            List<Image> images = new ArrayList<>();
            return new ResponseEntity<>(images, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filters/{id}")
    public ResponseEntity<Iterable<Image>> findAllFilterStore(@PathVariable Long id) {
        return new ResponseEntity<>(imageService.findAllFilterStore(id), HttpStatus.OK);
    }

    @GetMapping("/filters")
    public ResponseEntity<Iterable<Image>> findAllFilter() {
        return new ResponseEntity<>(imageService.findAllFilter(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Image> updateImage(@PathVariable Long id, @RequestBody Image image) {
        Optional<Image> image1 = imageService.findOneById(id);
        if (image1.isPresent()) {
            return new ResponseEntity<>(imageService.save(image), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        imageService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/product/{id}")
    public ResponseEntity<Message> deleteAllByImage(@PathVariable Long id) {
        Optional<Product> product = productService.findOneById(id);
        if (product.isPresent()) {
            product.get().setStatus(0);
            productService.save(product.get());
            return new ResponseEntity<>(new Message("done"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Message("error"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Iterable<Image>> findAllByCategoryId(@PathVariable Long id) {
        return new ResponseEntity<>(imageService.findAllByCategoryId(id), HttpStatus.OK);
    }

    @GetMapping("/product_name/{name}")
    public ResponseEntity<Iterable<Image>> findAllByProductName(@PathVariable String name) {
        return new ResponseEntity<>(imageService.findAllByProductName(name), HttpStatus.OK);
    }

    @GetMapping("/top-food")
    public ResponseEntity<Iterable<Image>> findAllTopFood() {
        return new ResponseEntity<>(imageService.findAllTopFood(), HttpStatus.OK);
    }
}
