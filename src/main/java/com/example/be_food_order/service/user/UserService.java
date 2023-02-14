package com.example.be_food_order.service.user;

import com.example.be_food_order.model.user.User;
import com.example.be_food_order.repository.user.IUserRepository;
import com.example.be_food_order.service.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements ICRUDService<User, Long> {
    @Autowired
    private IUserRepository userRepository;
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
}
