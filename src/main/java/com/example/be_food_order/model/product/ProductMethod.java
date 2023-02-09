package com.example.be_food_order.model.product;
import com.example.be_food_order.model.product.Product;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private double price;
    @NotNull
    private int quantity;
    @NotNull
    private String description;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Product product;

}
