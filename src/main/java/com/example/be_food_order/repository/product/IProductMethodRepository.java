package com.example.be_food_order.repository.product;

import com.example.be_food_order.model.product.ProductMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductMethodRepository extends JpaRepository<ProductMethod, Long> {

}
