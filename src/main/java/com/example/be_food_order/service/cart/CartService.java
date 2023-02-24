package com.example.be_food_order.service.cart;

import com.example.be_food_order.model.cart.Cart;
import com.example.be_food_order.model.cart.Invoice;
import com.example.be_food_order.model.cart.Payment;
import com.example.be_food_order.model.pojo.Filter;
import com.example.be_food_order.model.product.Product;
import com.example.be_food_order.model.store.Delivery;
import com.example.be_food_order.model.store.Store;
import com.example.be_food_order.model.user.Address;
import com.example.be_food_order.model.user.User;
import com.example.be_food_order.repository.cart.ICartRepository;
import com.example.be_food_order.repository.cart.IDeliveryRepository;
import com.example.be_food_order.repository.cart.IInvoiceRepository;
import com.example.be_food_order.repository.cart.IPaymentRepository;
import com.example.be_food_order.repository.product.IProductRepository;
import com.example.be_food_order.repository.user.IAddressRepository;
import com.example.be_food_order.service.product.ProductService;
import com.example.be_food_order.service.store.StoreService;
import com.example.be_food_order.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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
    private IAddressRepository addressRepository;
    @Autowired
    private StoreService storeService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private IProductRepository iProductRepository;

    public boolean save(Cart cart) {
        Optional<Product> product = productService.findOneById(cart.getProduct().getId());
        if (product.isPresent()) {
            Optional<Payment> payment = paymentRepository.findOne(cart.getUser().getId(), product.get().getStore().getId());
            Optional<Cart> cartCheck = cartRepository.findOne(cart.getUser().getId(), cart.getProduct().getId());
            if (cartCheck.isPresent() && !payment.isPresent()) {
                cartCheck.get().setQuantity(cartCheck.get().getQuantity() + 1);
                cartCheck.get().setPrice(cartCheck.get().getProduct().getProductMethod().getPrice() * cartCheck.get().getQuantity());
                cartRepository.save(cartCheck.get());
                return true;
            } else if (!Objects.equals(product.get().getStore().getUser().getId(), cart.getUser().getId())) {
                cartRepository.save(cart);
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

    public boolean changeQuantityOneCart(Long userId, Long productId) {
        Optional<Cart> cartCheck = cartRepository.findOne(userId, productId);
        if (cartCheck.isPresent()) {
            if (cartCheck.get().getQuantity() > 1) {
                cartCheck.get().setQuantity(cartCheck.get().getQuantity() - 1);
                cartCheck.get().setPrice(cartCheck.get().getQuantity() * cartCheck.get().getProduct().getProductMethod().getPrice());
                cartRepository.save(cartCheck.get());
                return true;
            } else {
                return false;
            }
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
    public boolean paymentCart(Long storeId, Long userId, Long addressId) {
        Optional<Store> store = storeService.findOneById(storeId);
        Optional<User> user = userService.findOneById(userId);
        Optional<Address> address = addressRepository.findById(addressId);
        String code = codePayment();
        if (store.isPresent() && user.isPresent() && address.isPresent()) {
            try {
                double price = cartRepository.totalPriceByPayment(user.get().getId(), store.get().getId());
                if (price <= user.get().getWallet()) {
                    paymentRepository.save(new Payment(0L, user.get(), store.get(), LocalDate.now(), price + 10, code, null, address.get(), 1));
                    Optional<Payment> payment = paymentRepository.findOneByCode(user.get().getId(), store.get().getId(), code);
                    if (payment.isPresent()) {
                        Iterable<Cart> listCarts = cartRepository.findALlCartByStoreAndUser(user.get().getId(), store.get().getId());
                        List<Cart> list  = (List<Cart>) listCarts;
                        if (list.size() > 0) {
                            for (Cart cart : listCarts) {
                                if (cart.getProduct().getStatus() != 1 && cart.getProduct().getStore().getStatus() != 1) {
                                    throw new Exception();
                                } else {
                                    invoiceRepository.save(new Invoice(0L, cart.getQuantity(), cart.getProduct(), payment.get()));
                                }
                            }
                            return true;
                        }else {
                            throw new Exception();
                        }
                    } else {
                        throw new Exception();
                    }
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean actionPayment(Long paymentId, String status) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        try {
            if (payment.isPresent()) {
                Optional<User> user = userService.findOneById(payment.get().getUser().getId());
                Optional<Store> store = storeService.findOneById(payment.get().getStore().getId());
                if (user.isPresent() && store.isPresent()) {
                    switch (status) {
                        case "approves":
                            Delivery delivery = deliveryRepository.findAll().get(0);
                            payment.get().setDelivery(delivery);
                            payment.get().setStatus(2);
                            user.get().setWallet(user.get().getWallet() - payment.get().getPrice() - 10);
                            store.get().setWallet(store.get().getWallet() + (payment.get().getPrice() * 80 / 100));
                            changeQuantityProduct(payment.get().getId());
                            deleteAllCart(payment.get().getUser().getId(), payment.get().getStore().getId());
                            break;
                        case "success":
                            payment.get().setStatus(3);
                            break;
                        case "cancel":
                            if (payment.get().getStatus() == 1) {
                                payment.get().setStatus(0);
                            } else {
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
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public Iterable<Payment> findAllPaymentByUser(Long userId) {
        Optional<User> user = userService.findOneById(userId);
        if (user.isPresent()) {
            return paymentRepository.findAllByUserId(user.get().getId());
        } else {
            return null;
        }
    }

    private String codePayment() {
        StringBuilder code = new StringBuilder("CG");
        for (int i = 0; i < 5; i++) {
            code.append(ThreadLocalRandom.current().nextInt(0, 10));
        }
        return code.toString();
    }

    public Optional<Payment> findPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Iterable<Invoice> findAllByPayment(Long paymentId) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        if (payment.isPresent()) {
            return invoiceRepository.findAllByPaymentId(payment.get().getId());
        } else {
            return null;
        }
    }

    private void changeQuantityProduct(Long paymentId) {
        Iterable<Invoice> invoices = invoiceRepository.findAllByPaymentId(paymentId);
        for (Invoice inv : invoices) {
            Optional<Product> product = productService.findOneById(inv.getProduct().getId());
            if (product.isPresent()) {
                product.get().getProductMethod().setQuantity(product.get().getProductMethod().getQuantity() + inv.getQuantity());
                productService.save(product.get());
            }
        }
    }

    public Iterable<Payment> findAllPaymentByStore(Long storeId) {
        Optional<Store> store = storeService.findOneById(storeId);
        if (store.isPresent()) {
            return paymentRepository.findAllPaymentByStore(store.get().getId());
        } else {
            return null;
        }
    }

    @Transactional
    public Iterable<Payment> filterPaymentByStore(Long storeId, Filter filter, String filterType) {
        try {
            Optional<Store> store = storeService.findOneById(storeId);
            if (store.isPresent()) {
                Iterable<Payment> payments;
                switch (filterType) {
                    case "code":
                        payments = findAllPaymentByCode(storeId, filter);
                        break;
                    case "day":
                        payments = findAllPaymentByDay(storeId, filter);
                        break;
                    case "month":
                        payments = findAllPaymentByMonth(storeId, filter);
                        break;
                    case "price":
                        payments = findAllPaymentByPrice(storeId, filter);
                        break;
                    case "buyer":
                        payments = findAllPaymentByBuyer(storeId, filter);
                        break;
                    case "phone":
                        payments = findAllPaymentByPhone(storeId, filter);
                        break;
                    default:
                        if (null == filter.getStatus() || 99 == filter.getStatus()) {
                            payments = paymentRepository.findAllPaymentByStore(storeId);
                        } else {
                            payments = paymentRepository.findAllPaymentByStoreAndStatus(storeId, filter.getStatus());
                        }
                }
                return payments;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private Iterable<Payment> findAllPaymentByCode(Long storeId, Filter filter) {
        if (null == filter.getStatus() || 99 == filter.getStatus()) {
            return paymentRepository.findAllPaymentByStoreAndCode(storeId, filter.getCode());
        } else {
            return paymentRepository.findAllPaymentByStoreAndCodeAndStatus(storeId, filter.getCode(), filter.getStatus());
        }
    }

    private Iterable<Payment> findAllPaymentByDay(Long storeId, Filter filter) {
        if (null == filter.getStatus() || 99 == filter.getStatus()) {
            return paymentRepository.findAllPaymentByStoreAndDay(storeId, filter.getDay());
        } else {
            return paymentRepository.findAllPaymentByStoreAndDayAndStatus(storeId, filter.getStatus(), filter.getDay());
        }
    }

    private Iterable<Payment> findAllPaymentByMonth(Long storeId, Filter filter) {
        String[] list = filter.getMonth().split("-");
        if (null == filter.getStatus() || 99 == filter.getStatus()) {
            return paymentRepository.findAllPaymentByStoreAndMonth(storeId, list[1], list[0]);
        } else {
            return paymentRepository.findAllPaymentByStoreAndMonthAndStatus(storeId, filter.getStatus(), list[1], list[0]);
        }
    }

    private Iterable<Payment> findAllPaymentByPrice(Long storeId, Filter filter) {
        String[] list = filter.getPrice().split(",");
        if (null == filter.getStatus() || 99 == filter.getStatus()) {
            return paymentRepository.findAllPaymentByStoreAndPrice(storeId, Double.parseDouble(list[0]), Double.parseDouble(list[1]));
        } else {
            return paymentRepository.findAllPaymentByStoreAndPriceAndStatus(storeId, filter.getStatus(), Double.parseDouble(list[0]), Double.parseDouble(list[1]));
        }
    }

    private Iterable<Payment> findAllPaymentByBuyer(Long storeId, Filter filter) {
        if (null == filter.getStatus() || 99 == filter.getStatus()) {
            return paymentRepository.findAllPaymentByStoreAndBuyer(storeId, filter.getBuyer());
        } else {
            return paymentRepository.findAllPaymentByStoreAndBuyerAndStatus(storeId, filter.getBuyer(), filter.getStatus());
        }
    }

    private Iterable<Payment> findAllPaymentByPhone(Long storeId, Filter filter) {
        if (null == filter.getStatus() || 99 == filter.getStatus()) {
            return paymentRepository.findAllPaymentByStoreAndPhone(storeId, filter.getPhone());
        } else {
            return paymentRepository.findAllPaymentByStoreAndPhoneAndStatus(storeId, filter.getPhone(), filter.getStatus());
        }
    }

    public Iterable<Invoice> findAllInvoiceByProductId(Long id) {
        return invoiceRepository.findAllByProductId(id);
    }
    public Iterable<Invoice> findAllInvoiceByProductName(String name, Long id){
        return invoiceRepository.findAllByProductNameContaining(name, id);
    }
}
