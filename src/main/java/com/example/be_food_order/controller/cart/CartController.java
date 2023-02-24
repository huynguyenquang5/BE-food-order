package com.example.be_food_order.controller.cart;

import com.example.be_food_order.model.cart.Cart;
import com.example.be_food_order.model.cart.Invoice;
import com.example.be_food_order.model.cart.Payment;
import com.example.be_food_order.model.message.Message;
import com.example.be_food_order.model.pojo.Filter;
import com.example.be_food_order.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cart")
@CrossOrigin("*")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<Iterable<Cart>> findAll() {
        return new ResponseEntity<>(cartService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Message> save(@RequestBody Cart cart) {
        if (cartService.save(cart)) {
            return new ResponseEntity<>(new Message("success"), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new Message("error"), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/one/user/{userId}/product/{productId}")
    public ResponseEntity<Cart> deleteOne(@PathVariable("userId") Long userId,
                                          @PathVariable("productId") Long productId) {
        boolean check = cartService.deleteOneCart(userId, productId);
        if (check) {
            return new ResponseEntity<>(new Cart(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/quantity/one/user/{userId}/product/{productId}")
    public ResponseEntity<Cart> changeOneQuantity(@PathVariable("userId") Long userId,
                                                  @PathVariable("productId") Long productId) {
        boolean check = cartService.changeQuantityOneCart(userId, productId);
        if (check) {
            return new ResponseEntity<>(new Cart(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/all/store/{storeId}/user/{userId}")
    public ResponseEntity<Cart> deleteAll(@PathVariable("userId") Long userId,
                                          @PathVariable("storeId") Long storeId) {
        boolean check = cartService.deleteAllCart(userId, storeId);
        if (check) {
            return new ResponseEntity<>(new Cart(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/store/{storeId}/user/{userId}")
    public ResponseEntity<Iterable<Cart>> findAllByStoreAndUser(@PathVariable("storeId") Long storeId,
                                                                @PathVariable("userId") Long userId) {
        return new ResponseEntity<Iterable<Cart>>(cartService.findAllByStoreAndUser(storeId, userId), HttpStatus.OK);
    }

    @GetMapping("/payment/{id}/action/{status}")
    public ResponseEntity<Message> actionStatusPayment(@PathVariable("id") Long id,
                                                       @PathVariable("status") String status) {
        if (cartService.actionPayment(id, status)) {
            return new ResponseEntity<>(new Message("success"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Message("error"), HttpStatus.OK);
        }
    }

    @GetMapping("/payment-cart/store/{storeId}/user/{userId}/address/{addressId}")
    public ResponseEntity<Message> paymentCart(@PathVariable("storeId") Long storeId,
                                               @PathVariable("userId") Long userId,
                                               @PathVariable("addressId") Long addressId) {
        if (cartService.paymentCart(storeId, userId, addressId)) {
            return new ResponseEntity<>(new Message("success"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Message("error"), HttpStatus.OK);
        }
    }

    @GetMapping("/payment-order/user/{userId}")
    public ResponseEntity<Iterable<Payment>> findAllPaymentByUser(@PathVariable("userId") Long userId) {
        return new ResponseEntity<Iterable<Payment>>(cartService.findAllPaymentByUser(userId), HttpStatus.OK);
    }

    @GetMapping("/payment-detail/payment/{paymentId}")
    public ResponseEntity<Payment> findPaymentById(@PathVariable("paymentId") Long paymentId) {
        Optional<Payment> payment = cartService.findPaymentById(paymentId);
        if (payment.isPresent()) {
            return new ResponseEntity<>(payment.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/payment-detail/payment/{paymentId}/list-invoice")
    public ResponseEntity<Iterable<Invoice>> listInvoiceByPayment(@PathVariable("paymentId") Long paymentId) {
        Iterable<Invoice> invoices = cartService.findAllByPayment(paymentId);
        if (null != invoices) {
            return new ResponseEntity<>(invoices, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/payment-list/store/{storeId}")
    public ResponseEntity<Iterable<Payment>> findAllPaymentByStore(@PathVariable("storeId") Long storeId) {
        Iterable<Payment> Payments = cartService.findAllPaymentByStore(storeId);
        if (null != Payments) {
            return new ResponseEntity<>(Payments, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/filter/payment-list/store/{storeId}/{filterType}")
    public ResponseEntity<Iterable<Payment>> filterPayment(@PathVariable("storeId") Long storeId,
                                                           @PathVariable("filterType") String filterType,
                                                           @RequestBody Filter filter) {
        Iterable<Payment> payments =cartService.filterPaymentByStore(storeId, filter,filterType);
        if(null != payments) {
            return new ResponseEntity<>(payments, HttpStatus.OK);
        }else {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/invoice-list/product/{id}")
    public ResponseEntity<Iterable<Invoice>> findAllInvoiceByProductId(@PathVariable Long id){
        return new ResponseEntity<>(cartService.findAllInvoiceByProductId(id),HttpStatus.OK);
    }
    @GetMapping("/invoice-list/product/name/{name}/{id}")
    public ResponseEntity<Iterable<Invoice>> findAllInvoiceByProductName(@PathVariable String name, @PathVariable Long id){
        return new ResponseEntity<>(cartService.findAllInvoiceByProductName(name,id),HttpStatus.OK);
    }
}
