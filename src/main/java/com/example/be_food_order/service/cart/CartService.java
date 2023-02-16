package com.example.be_food_order.service.cart;

import com.example.be_food_order.model.cart.Cart;
import com.example.be_food_order.repository.cart.ICartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CartService {
    @Autowired
    private ICartRepository cartRepository;

    public Cart save(Cart cart){
        Optional<Cart> cartCheck = cartRepository.findOne(cart.getUser().getId(),cart.getProduct().getId());
        if (cartCheck.isPresent()){
            cartCheck.get().setQuantity(cartCheck.get().getQuantity()+1);
            cartCheck.get().setPrice(cartCheck.get().getProduct().getProductMethod().getPrice()*cartCheck.get().getQuantity());
            return cartRepository.save(cartCheck.get());
        }else {
            return cartRepository.save(cart);
        }
    }
    public Iterable<Cart> findAll(){
        return cartRepository.findAll();
    }
    public boolean deleteOneCart(Long userId,Long productId){
        Optional<Cart> cartCheck = cartRepository.findOne(userId,productId);
        if (cartCheck.isPresent()){
            cartRepository.deleteOneCart(userId,productId);
            return true;
        }else {
            return false;
        }
    }
}