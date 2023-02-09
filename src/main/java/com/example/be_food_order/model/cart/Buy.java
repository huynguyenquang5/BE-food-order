package com.example.be_food_order.model.cart;
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
public class Buy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private int quantity;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Payment payment;
}
