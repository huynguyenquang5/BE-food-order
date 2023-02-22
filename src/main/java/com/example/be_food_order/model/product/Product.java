package com.example.be_food_order.model.product;

import com.example.be_food_order.model.store.Store;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Integer status = 1;
    @ManyToOne
    private ProductMethod productMethod;
    @ManyToOne
    private Store store;
}
