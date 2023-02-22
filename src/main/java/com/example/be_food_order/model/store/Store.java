package com.example.be_food_order.model.store;

import com.example.be_food_order.model.user.User;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Check(constraints = "wallet >= 0")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String nameStore;
    @NotNull
    @Column(unique = true)
    private String phoneStore;
    @NotNull
    private String addressStore;
    @NotNull
    private String logo;
    @NotNull
    private String description;
    @NotNull
    private Double wallet = 0.0;
    @NotNull
    private Integer status = 1;
    @ManyToOne
    private User user;
}
