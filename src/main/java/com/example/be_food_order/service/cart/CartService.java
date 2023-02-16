package com.example.be_food_order.service.cart;

import com.example.be_food_order.model.cart.Cart;
import com.example.be_food_order.model.cart.Invoice;
import com.example.be_food_order.model.cart.Payment;
import com.example.be_food_order.model.product.Product;
import com.example.be_food_order.model.store.Delivery;
import com.example.be_food_order.model.store.Store;
import com.example.be_food_order.model.user.User;
import com.example.be_food_order.repository.cart.ICartRepository;
import com.example.be_food_order.repository.cart.IDeliveryRepository;
import com.example.be_food_order.repository.cart.IInvoiceRepository;
import com.example.be_food_order.repository.cart.IPaymentRepository;
import com.example.be_food_order.service.product.ProductService;
import com.example.be_food_order.service.store.StoreService;
import com.example.be_food_order.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class CartService {
    @Autowired
    private ICartRepository cartRepository;
    @Autowired
    private IPaymentRepository paymentRepository;
    @Autowired
    private IInvoiceRepository invoiceRepository;
    @Autowired
    private IDeliveryRepository deliveryRepository;
    @Autowired
    private StoreService storeService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    public boolean save(Cart cart) {
        Optional<Product> product = productService.findOneById(cart.getProduct().getId());
        Optional<Payment> payment = paymentRepository.findOne(cart.getUser().getId(), product.get().getStore().getId());
        if (!payment.isPresent()) {
            Optional<Cart> cartCheck = cartRepository.findOne(cart.getUser().getId(), cart.getProduct().getId());
            if (cartCheck.isPresent()) {
                cartCheck.get().setQuantity(cartCheck.get().getQuantity() + 1);
                cartCheck.get().setPrice(cartCheck.get().getProduct().getProductMethod().getPrice() * cartCheck.get().getQuantity());
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Iterable<Cart> findAll() {
        return cartRepository.findAll();
    }

    public boolean deleteOneCart(Long userId, Long productId) {
        Optional<Cart> cartCheck = cartRepository.findOne(userId, productId);
        if (cartCheck.isPresent()) {
            cartRepository.deleteOneCart(userId, productId);
            return true;
        } else {
            return false;
        }
    }

    public Iterable<Cart> findAllByStoreAndUser(Long storeId, Long userId) {
        return cartRepository.findALlCartByStoreAndUser(userId, storeId);
    }

    public boolean deleteAllCart(Long userId, Long storeId) {
        try {
            cartRepository.deleteAllCart(userId, storeId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public boolean paymentCart(Long storeId, Long userId) {
        Optional<Store> store = storeService.findOneById(storeId);
        Optional<User> user = userService.findOneById(userId);
        if (store.isPresent() && user.isPresent()) {
            try {
                double price = cartRepository.totalPriceByPayment(user.get().getId(), store.get().getId());
                paymentRepository.save(new Payment(0L, user.get(), store.get(), LocalDate.now(), price, null, 1));
                Payment payment = paymentRepository.findOne(user.get().getId(), store.get().getId()).get();
                Iterable<Cart> listCarts = cartRepository.findALlCartByStoreAndUser(user.get().getId(), store.get().getId());
                for (Cart cart : listCarts) {
                    invoiceRepository.save(new Invoice(0L, cart.getQuantity(), cart.getProduct(), payment));
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean actionPayment(Long paymentId,String status) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        if (payment.isPresent()) {
            switch (status) {
                case "approves":
                    Delivery delivery = deliveryRepository.findAll().get(0);
                    payment.get().setDelivery(delivery);
                    payment.get().setStatus(2);
                    break;
                case "success":
                    payment.get().setStatus(3);
                    break;
                case "cancels":
                    if (payment.get().getStatus() == 1) {
                        payment.get().setStatus(0);
                    }else {
                        return false;
                    }
                    break;
                default:
                    payment.get().setStatus(0);
            }
            return true;
        } else {
            return false;
        }
    }
}
