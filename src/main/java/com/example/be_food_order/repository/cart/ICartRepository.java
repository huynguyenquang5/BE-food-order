package com.example.be_food_order.repository.cart;

import com.example.be_food_order.model.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICartRepository extends JpaRepository<Cart,Long> {
    @Query(value="select * from cart where cart.product_id = :productId and cart.user_id = :userId ", nativeQuery=true)
    Optional<Cart> findOne(@Param("userId") Long userId,@Param("productId")  Long productId);
    @Modifying
    @Query(value="delete from cart where cart.product_id = :productId and cart.user_id = :userId ", nativeQuery=true)
    void deleteOneCart(@Param("userId") Long userId, @Param("productId") Long productId);
    @Query(value="select c.id, c.price, c.product_id, c.quantity, c.user_id from cart as c join product on" +
            "  c.product_id = product.id where c.user_id = :userId and product.store_id = :storeId", nativeQuery=true)
    Iterable<Cart> findALlCartByStoreAndUser(@Param("userId") Long userId,@Param("storeId")  Long storeId);
}
