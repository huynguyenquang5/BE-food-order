package com.example.be_food_order.model.user;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Check(constraints = "wallet >= 0")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @Column(unique = true)
    @NotNull
    private String username;
    @NotNull
    private String password;
    @Column(unique = true)
    @NotNull
    private String phone;
    @Column(unique = true)
    @NotNull
    private String email;
    @NotNull
    private Double wallet = 0.0;
    @NotNull
    private Integer status = 1;
    @ManyToMany()
    private Set<Role> roles;
    private String confirmPassword;
}
