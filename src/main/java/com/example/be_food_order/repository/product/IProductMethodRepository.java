package com.example.be_food_order.repository.product;
import com.example.be_food_order.model.product.ProductMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductMethodRepository extends JpaRepository<ProductMethod, Long> {
    @Query(value = "select * from product_method where id = (select MAX(id) from product_method)", nativeQuery = true)
    ProductMethod findLast();
    @Query(value = "select id from product_method where category_id = ?1", nativeQuery = true)
    List<Long> findIdProductMethod(Long id);

}
