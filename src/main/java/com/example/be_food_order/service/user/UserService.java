package com.example.be_food_order.service.user;

import com.example.be_food_order.model.user.User;
import com.example.be_food_order.repository.user.IUserRepository;
import com.example.be_food_order.service.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserService implements ICRUDService {

  @Autowired
  private IUserRepository iUserRepository;
    @Override
    public Object save(Object o) {
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        return iUserRepository.findAll();
    }

    @Override
    public User findByUserName(String userName) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public boolean checkUser(User user) {
        return false;
    }

    @Override
    public boolean isRegister(User user) {
        return false;
    }

    @Override
    public Optional findOneById(Object o) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Object o) {

    }
}
