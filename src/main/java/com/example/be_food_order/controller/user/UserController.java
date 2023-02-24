package com.example.be_food_order.controller.user;

import com.example.be_food_order.model.message.Message;
import com.example.be_food_order.model.user.Address;
import com.example.be_food_order.model.user.Role;
import com.example.be_food_order.model.user.User;
import com.example.be_food_order.sercurity.jwt.JwtService;
import com.example.be_food_order.service.user.RoleService;
import com.example.be_food_order.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


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
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @GetMapping()
    public ResponseEntity<Iterable<User>> findAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        return new ResponseEntity<>(userService.findOneById(id).get(), HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<Iterable<Role>> findAllRoles(){
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOptional = userService.findOneById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setId(userOptional.get().getId());
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }
    @PostMapping("/address")
    public ResponseEntity<Address> saveAddress(@RequestBody Address address){
        return new ResponseEntity<>(userService.saveAddress(address), HttpStatus.OK);
    }
    @GetMapping("/address/user/{userId}")
    public ResponseEntity<Iterable<Address>> findAllAddressByUser(@PathVariable Long userId){
        return new ResponseEntity<>(userService.findAllAddressByUser(userId), HttpStatus.OK);
    }
    @DeleteMapping("/address/{id}")
    public ResponseEntity<Message> deleteAddressByUser(@PathVariable Long id){
        userService.deleteAddressById(id);
        return new ResponseEntity<>(new Message("done"), HttpStatus.OK);
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<User> findUserByName(@PathVariable String username){
        if (userService.findByUsername(username).isPresent()){return new ResponseEntity<>(userService.findByUsername(username).get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/change/{id}")
    public ResponseEntity<User> changePassword(@RequestBody User user, @PathVariable Long id){
        Optional<User> userChange = userService.findOneById(id);
        if (userChange.isPresent()){
            user.setId(userChange.get().getId());
            user.setName(userChange.get().getName());
            user.setUsername(userChange.get().getUsername());
            user.setPhone(userChange.get().getPhone());
            user.setStatus(userChange.get().getStatus());
            user.setWallet(userChange.get().getWallet());
            user.setConfirmPassword(user.getConfirmPassword());
            user.setEmail(userChange.get().getEmail());
            user.setRoles(userChange.get().getRoles());
            String encodePassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodePassword);
            return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
