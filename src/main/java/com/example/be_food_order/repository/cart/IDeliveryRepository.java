package com.example.be_food_order.repository.cart;

import com.example.be_food_order.model.store.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDeliveryRepository extends JpaRepository<Delivery,Long> {
}
