package com.example.be_food_order.service;

import com.example.be_food_order.model.user.User;

import java.util.Optional;

public interface ICRUDService<T, K>  {
    T save(T t);
    Iterable<T> findAll();

    User findByUserName(String userName);
    User findByEmail(String email);
    boolean checkUser(User user);

    boolean isRegister(User user);
    Optional<T> findOneById(K k);

    void deleteById(K k);

}
