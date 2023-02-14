package com.example.be_food_order.repository.product;

import com.example.be_food_order.model.product.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IImageRepository extends JpaRepository<Image, Long> {
    Iterable<Image> findAllByProductId(Long id);
    @Query(value="select * from image group by image.product_id", nativeQuery=true)
    Iterable<Image> findAllFilter();
    @Query(value="select * from image join product on image.product_id = product.id where product.store_id= :storeId group by image.product_id", nativeQuery=true)
    Iterable<Image> findAllFilterStore(@Param("storeId") Long storeId);
    @Transactional
    @Modifying
    @Query(value="delete from image where image.product_id= :productId", nativeQuery=true)
    void deleteListImageByProduct(@Param("productId") Long productId);
}
