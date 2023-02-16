package com.example.be_food_order.repository.cart;

import com.example.be_food_order.model.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICartRepository extends JpaRepository<Cart,Long> {
    @Query(value="select * from cart where cart.product_id = :productId and cart.user_id = :userId ", nativeQuery=true)
    Optional<Cart> findOne(Long userId, Long productId);
    @Modifying
    @Query(value="delete from cart where cart.product_id = :productId and cart.user_id = :userId ", nativeQuery=true)
    void deleteOneCart(Long userId, Long productId);
}
