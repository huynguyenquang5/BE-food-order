package com.example.be_food_order.repository.user;

import com.example.be_food_order.model.user.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAddressRepository extends JpaRepository<Address,Long> {
    Iterable<Address> findAllByUserId(Long userId);
}
