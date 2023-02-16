package com.example.be_food_order.repository.user;

import com.example.be_food_order.model.store.Store;
import com.example.be_food_order.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name);
}
