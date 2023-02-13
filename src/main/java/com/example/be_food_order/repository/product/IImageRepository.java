package com.example.be_food_order.repository.product;

import com.example.be_food_order.model.product.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IImageRepository extends JpaRepository<Image, Long> {
    Iterable<Image> findAllByProductId(Long id);
    @Query(value="select * from image group by image.product_id;", nativeQuery=true)
    Iterable<Image> findAllFilter();
}
