package com.example.be_food_order.controller.store;
import com.example.be_food_order.model.store.Store;
import com.example.be_food_order.service.store.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/store")
@CrossOrigin("*")
public class StoreController {
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
    @GetMapping("/user/{id}")
    public ResponseEntity<Store> findByUserId(@PathVariable Long id) {
        Optional<Store> store = storeService.findStoreByUserId(id);
        if (!store.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(store.get(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Store> createStore(@RequestBody Store store) {
        return new ResponseEntity<>(storeService.save(store), HttpStatus.CREATED);
    }
    @PutMapping("user/{id}")
    public ResponseEntity<Store> updateStore(@PathVariable Long id, @RequestBody Store store) {
        Optional<Store> storeOptional = storeService.findStoreByUserId(id);
        if (!storeOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        store.setId(storeOptional.get().getId());
        return new ResponseEntity<>(storeService.save(store), HttpStatus.OK);
    }
}
