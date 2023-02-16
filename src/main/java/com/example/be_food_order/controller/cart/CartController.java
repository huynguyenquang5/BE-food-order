package com.example.be_food_order.controller.cart;

import com.example.be_food_order.model.cart.Cart;
import com.example.be_food_order.model.message.Message;
import com.example.be_food_order.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@CrossOrigin("*")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<Iterable<Cart>> findAll(){
        return new ResponseEntity<>(cartService.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Cart> save(@RequestBody Cart cart){
        return new ResponseEntity<>(cartService.save(cart), HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/user/{userId}/product/{productId}")
    public ResponseEntity<Cart> deleteOne(@PathVariable("userId") Long userId,
                                          @PathVariable("productId") Long productId){
        boolean check = cartService.deleteOneCart(userId, productId);
        if (check){
            return new ResponseEntity<>(new Cart(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/store/{storeId}/user/{userId}")
    public ResponseEntity<Iterable<Cart>> findAllByStoreAndUser(@PathVariable("userId") Long userId,
                                                                @PathVariable("storeId") Long storeId){
        return new ResponseEntity<Iterable<Cart>>(cartService.findAllByStoreAndUser(storeId, userId),HttpStatus.OK);
    }
}
