package com.example.be_food_order.repository.cart;

import com.example.be_food_order.model.cart.Cart;
import com.example.be_food_order.model.cart.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment,Long> {
    @Query(value="select * from payment where (payment.store_id = :storeId and payment.user_id = :userId) and (payment.status = 1) ", nativeQuery=true)
    Optional<Payment> findOne(@Param("userId") Long userId, @Param("storeId")  Long storeId);
}
