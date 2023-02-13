package com.example.be_food_order.repository;

import com.example.be_food_order.model.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStoreRepository extends JpaRepository<Store,Long> {
}
