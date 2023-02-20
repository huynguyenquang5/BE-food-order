package com.example.be_food_order.controller.user;

import com.example.be_food_order.model.user.Address;
import com.example.be_food_order.service.user.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/{id}")
    public ResponseEntity<Iterable<Address>> findAllByUserID(@PathVariable Long id) {
        return new ResponseEntity<>(addressService.findAllByUserId(id), HttpStatus.OK);
    }

    @GetMapping("/addressId={id}")
    public ResponseEntity<Address> findById(@PathVariable Long id) {
        Optional<Address> address = addressService.findOneById(id);
        if (!address.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(address.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Address> create(@RequestBody Address address) {
        return new ResponseEntity<>(addressService.save(address), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> update(@PathVariable Long id, @RequestBody Address address) {
        Optional<Address> addressOptional = addressService.findOneById(id);
        if (!addressOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        address.setId(addressOptional.get().getId());
        return new ResponseEntity<>(addressService.save(address), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Address> delete(@PathVariable Long id) {
        Optional<Address> address = addressService.findOneById(id);
        if (!address.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        addressService.deleteById(id);
        return new ResponseEntity<>(address.get(), HttpStatus.NO_CONTENT);
    }
}
