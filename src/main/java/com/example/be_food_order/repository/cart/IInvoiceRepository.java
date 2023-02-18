package com.example.be_food_order.repository.cart;

import com.example.be_food_order.model.cart.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInvoiceRepository extends JpaRepository<Invoice,Long> {
}
