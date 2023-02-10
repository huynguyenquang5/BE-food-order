package com.example.be_food_order.service;

import java.util.Optional;

public interface ICRUDService<T, K> {
    Iterable<T> findAll();
    Optional<T> findOneById(K k);
    T save(T t);
    void deleteById(K k);
}
