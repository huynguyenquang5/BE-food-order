package com.example.be_food_order.service.user;

import com.example.be_food_order.model.user.Address;
import com.example.be_food_order.model.user.User;
import com.example.be_food_order.model.user.UserPrinciple;
import com.example.be_food_order.repository.user.IAddressRepository;
import com.example.be_food_order.repository.user.IUserRepository;
import com.example.be_food_order.service.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements ICRUDService<User, Long>, UserDetailsService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IAddressRepository addressRepository;
    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findOneById(Long aLong) {
        return userRepository.findById(aLong);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long aLong) {
        userRepository.deleteById(aLong);
    }
    public Optional<User> findByUsername(String name){
        return userRepository.findByUsername(name);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return UserPrinciple.build(userOptional.get());
    }
    public Address saveAddress(Address address){
        return addressRepository.save(address);
    }
    public Iterable<Address> findAllAddressByUser(Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return addressRepository.findAllByUserId(userOptional.get().getId());
        }else {
            return null;
        }
    }
    public void deleteAddressById(Long id){
        Optional<Address> address = addressRepository.findById(id);
        if (address.isPresent()) {
            addressRepository.deleteById(address.get().getId());
        }
    }
}
