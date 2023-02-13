package com.example.be_food_order.service;

import com.example.be_food_order.model.store.Store;
import com.example.be_food_order.repository.IStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StoreService implements ICRUDService<Store, Long>{
    @Autowired
    private IStoreRepository storeRepository;
    @Override
    public Iterable<Store> findAll() {
        return null;
    }

    @Override
    public Optional<Store> findOneById(Long id) {
        Optional<Store> store = storeRepository.findById(id);
        if (store.isPresent()) {
            return store;
        }else {
            return null;
        }
    }

    @Override
    public Store save(Store store) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
