package com.example.be_food_order.controller.user;

import com.example.be_food_order.model.user.Role;
import com.example.be_food_order.model.user.User;
import com.example.be_food_order.sercurity.jwt.JwtResponse;
import com.example.be_food_order.sercurity.jwt.JwtService;
import com.example.be_food_order.service.user.RoleService;
import com.example.be_food_order.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
@CrossOrigin("*")
public class AccountController {
    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @PostMapping("/register")
    public ResponseEntity<User> create(@RequestBody User user){
        Optional<User> checkUser = userService.findByUsername(user.getUsername());
        String encodePassword;
        encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        if(!checkUser.isPresent()){
            Role role = roleService.findByName("USER");
            user.setRoles(new HashSet<>());
            user.getRoles().add(role);
            return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername()).get();
        return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), currentUser.getUsername(), userDetails.getAuthorities(),currentUser.getStatus(),currentUser.getEmail(), currentUser.getPhone(), currentUser.getWallet()));
    }

}
