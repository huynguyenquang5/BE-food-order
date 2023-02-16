package com.example.be_food_order.service.user;

import com.example.be_food_order.model.user.Role;
import com.example.be_food_order.repository.user.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private IRoleRepository roleRepository;

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
    public List<Role> findAll(){
        return roleRepository.findAll();
    }
}
