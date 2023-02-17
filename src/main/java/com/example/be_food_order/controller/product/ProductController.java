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


    @GetMapping()
    public ResponseEntity<Iterable<Product>> findAllProduct(){
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findOne(@PathVariable Long id){
        if(productService.findOneById(id).isPresent()){
            return new ResponseEntity<>(productService.findOneById(id).get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/store/{id}")
    public ResponseEntity<Iterable<Product>> findAllByStore(@PathVariable Long id){
        if(productService.findAllByStore(id) != null){
            return new ResponseEntity<>(productService.findAllByStore(id), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        productService.deleteById(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product){
        ProductMethod productMethod = product.getProductMethod();
        productMethodService.save(productMethod);
        ProductMethod productMethod1 = productMethodService.findOneById(productMethod.getId()).get();
        product.setProductMethod(productMethod1);
        if (productService.findOneById(id).isPresent()){
            return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);

        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/search/{name}")
    public ResponseEntity<Iterable<Product>> findAllByName(@PathVariable String name){
        return new ResponseEntity<>(productService.findAllByName(name), HttpStatus.OK);
    }
}
