package com.example.be_food_order.repository.product;

import com.example.be_food_order.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "select * from product where id = (select MAX(id) from product)", nativeQuery = true)
    Product findLast();
    Iterable<Product> findAllByStoreId(Long storeId);
    Iterable<Product> findAllByProductMethod_Category_Id(Long id);
    Iterable<Product> findAllByNameContaining(String name);


}
