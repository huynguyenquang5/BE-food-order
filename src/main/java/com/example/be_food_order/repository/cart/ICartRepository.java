package com.example.be_food_order.repository.cart;

import com.example.be_food_order.model.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    @Modifying
    @Transactional
    @Query(value="delete c from cart as c join product on c.product_id = product.id where c.user_id = :userId and product.store_id = :storeId", nativeQuery=true)
    void deleteAllCart(@Param("userId") Long userId, @Param("storeId") Long storeId);
    @Query(value="select sum(pm.price*c.quantity) from cart as c join product as p on  c.product_id = p.id \n" +
            "\t join product_method as pm on p.product_method_id = pm.id \n" +
            "\t where c.user_id = :userId and p.store_id = :storeId ", nativeQuery=true)
    double totalPriceByPayment(@Param("userId") Long userId,@Param("storeId")  Long storeId);
}
