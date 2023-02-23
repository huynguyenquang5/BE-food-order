package com.example.be_food_order.repository.cart;

import com.example.be_food_order.model.cart.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IInvoiceRepository extends JpaRepository<Invoice,Long> {
    Iterable<Invoice> findAllByPaymentId(Long id);
    Iterable<Invoice> findAllByProductId(Long id);
    @Query(value = "SELECT invoice.* FROM invoice  INNER JOIN product ON invoice.product_id = product.id where product.name like %:name% and product.store_id= :store_id", nativeQuery = true)
    Iterable<Invoice> findAllByProductNameContaining(@Param("name" ) String name, @Param("store_id") Long store_id);
}
