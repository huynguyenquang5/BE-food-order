package com.example.be_food_order.service.store;

import com.example.be_food_order.model.store.Store;
import com.example.be_food_order.repository.store.IStoreRepository;
import com.example.be_food_order.service.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StoreService implements ICRUDService<Store, Long> {
    @Autowired
    private IStoreRepository storeRepository;
    @Override
    public Iterable<Store> findAll() {
        return storeRepository.findAll();
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
        return storeRepository.save(store);
    }

    @Override
    public void deleteById(Long aLong) {

    }

    public Optional<Store> findStoreByUserId(Long userId) {
        Optional<Store> store = storeRepository.findStoreByUserId(userId);
        if (store.isPresent()) {
            return store;
        } else {
            return null;
        }
    }
}
