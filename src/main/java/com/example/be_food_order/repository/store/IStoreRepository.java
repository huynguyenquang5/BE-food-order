package com.example.be_food_order.repository.store;

import com.example.be_food_order.model.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IStoreRepository extends JpaRepository<Store,Long> {
    @Query(value = "select * from store where user_id = :userId", nativeQuery = true)
    Optional<Store> findStoreByUserId(@Param("userId") Long userId);
}
