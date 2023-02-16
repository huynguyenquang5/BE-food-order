package com.example.be_food_order.controller.user;

import com.example.be_food_order.model.user.Role;
import com.example.be_food_order.model.user.User;
import com.example.be_food_order.service.user.RoleService;
import com.example.be_food_order.service.user.UserService;
import org.jetbrains.annotations.ApiStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
//    @PostMapping('/login')
//    public ResponseEntity<?> login(@RequestBody User user){
//
//    }
    @GetMapping()
    public ResponseEntity<Iterable<User>> findAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        return new ResponseEntity<>(userService.findOneById(id).get(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> create(@RequestBody User user){
        Optional<User> checkUser = userService.findByUsername(user.getUsername());
        String encodePassword;
        encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        if(!checkUser.isPresent()){
            Role role = roleService.findByName("BUYER");
            user.setRoles(new HashSet<>());
            user.getRoles().add(role);
            return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/roles")
    public ResponseEntity<Iterable<Role>> findAllRoles(){
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }
}
