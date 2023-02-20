package com.example.be_food_order.model.cart;
import com.example.be_food_order.model.store.Delivery;
import com.example.be_food_order.model.store.Store;
import com.example.be_food_order.model.user.Address;
import com.example.be_food_order.model.user.User;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Store store;
    @NotNull
    private LocalDate date;
    @NotNull
    private double price;
    @NotNull
    private String code;
    @ManyToOne
    private Delivery delivery;
    @ManyToOne
    private Address address;
    @NotNull
    private int status;
}
