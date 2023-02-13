package com.example.be_food_order.controller;
import com.example.be_food_order.model.store.Store;
import com.example.be_food_order.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.Optional;

@RestController
@RequestMapping("/store")
@CrossOrigin("*")
public class storeController {
    @Autowired
    private StoreService storeService;
    @GetMapping("/{id}")
    public ResponseEntity<Store> findById(@PathVariable Long id){
        Optional<Store> store = storeService.findOneById(id);
        if (store.isPresent()){
            return new ResponseEntity<>(store.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping()
    public ResponseEntity<Iterable<Store>> findAll(){
        return new ResponseEntity<>(storeService.findAll(),HttpStatus.OK);
    }
}
