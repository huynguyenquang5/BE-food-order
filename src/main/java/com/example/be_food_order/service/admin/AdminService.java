package com.example.be_food_order.service.admin;

import com.example.be_food_order.service.user.RoleService;
import com.example.be_food_order.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    public void activeBlockUser(Long id, Integer status) {
        if (userService.findOneById(id).isPresent()) {
            userService.findOneById(id).get().setStatus(status);
        }
    }

    public void addRoleMerchant(Long id) {
        if (userService.findOneById(id).isPresent()) {
            userService.findOneById(id).get().setStatus(1);
            userService.findOneById(id).get().getRoles().add(roleService.findByName("MERCHANT"));
        }
    }

    public void addRoleMerchantPartner(Long id) {
        if (userService.findOneById(id).isPresent()) {
            userService.findOneById(id).get().setStatus(1);
            userService.findOneById(id).get().getRoles().add(roleService.findByName("MERCHANT_PARTNER"));
        }
    }
}
