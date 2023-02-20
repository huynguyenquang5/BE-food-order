package com.example.be_food_order.service.user;

import com.example.be_food_order.model.user.Address;
import com.example.be_food_order.repository.user.IAddressRepository;
import com.example.be_food_order.service.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService implements ICRUDService<Address, Long> {
    @Autowired
    private IAddressRepository addressRepository;

    public Iterable<Address> findAllByUserId(Long id) {
        return addressRepository.findAllByUserId(id);
    }

    @Override
    public Iterable<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Optional<Address> findOneById(Long aLong) {
        return addressRepository.findById(aLong);
    }

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void deleteById(Long aLong) {
        addressRepository.deleteById(aLong);
    }
}
