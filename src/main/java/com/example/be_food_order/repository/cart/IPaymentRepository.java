package com.example.be_food_order.repository.cart;

import com.example.be_food_order.model.cart.Cart;
import com.example.be_food_order.model.cart.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, Long> {
    @Query(value = "select * from payment where (payment.store_id = :storeId and payment.user_id = :userId) and (payment.status = 1 ) ", nativeQuery = true)
    Optional<Payment> findOne(@Param("userId") Long userId, @Param("storeId") Long storeId);

    @Query(value = "select * from payment where (payment.store_id = :storeId and payment.user_id = :userId) and (payment.status = 1 and payment.code= :code) ", nativeQuery = true)
    Optional<Payment> findOneByCode(@Param("userId") Long userId, @Param("storeId") Long storeId, @Param("code") String code);

    Iterable<Payment> findAllByUserId(Long userId);

    @Query(value = "select p.id, p.date, p.price, p.status, p.delivery_id, p.store_id, p.user_id, p.address_id, p.code \n" +
            "from\n" +
            " payment p join store s on p.store_id = s.id \n" +
            "where s.id= :storeId ", nativeQuery = true)
    Iterable<Payment> findAllPaymentByStore(@Param("storeId") Long storeId);

    //done
    @Query(value = "select p.id, p.date, p.price, p.status, p.delivery_id, p.store_id, p.user_id, p.address_id, p.code \n" +
            "from\n" +
            " payment p join store s on p.store_id = s.id \n" +
            "where s.id= :storeId and p.code like %:code% ", nativeQuery = true)
    Iterable<Payment> findAllPaymentByStoreAndCode(@Param("storeId") Long storeId, @Param("code") String code);

    //done
    @Query(value = "select p.id, p.date, p.price, p.status, p.delivery_id, p.store_id, p.user_id, p.address_id, p.code \n" +
            "from\n" +
            " payment p join store s on p.store_id = s.id \n" +
            "where (s.id= :storeId and p.code like %:code%) and p.status= :status ", nativeQuery = true)
    Iterable<Payment> findAllPaymentByStoreAndCodeAndStatus(@Param("storeId") Long storeId, @Param("code") String code, @Param("status") Long status);

    //done
    @Query(value = "select p.id, p.date, p.price, p.status, p.delivery_id, p.store_id, p.user_id, p.address_id, p.code \n" +
            "from\n" +
            " store s join payment p on s.id =  p.store_id join user u on p.user_id = u.id \n" +
            "where (s.id= :storeId and u.name like %:name%) ", nativeQuery = true)
    Iterable<Payment> findAllPaymentByStoreAndBuyer(@Param("storeId") Long storeId, @Param("name") String name);

    //done
    @Query(value = "select p.id, p.date, p.price, p.status, p.delivery_id, p.store_id, p.user_id, p.address_id, p.code \n" +
            "from\n" +
            " store s join payment p on s.id =  p.store_id join user u on p.user_id = u.id \n" +
            "where (s.id= :storeId and u.name like %:name%) and p.status= :status ", nativeQuery = true)
    Iterable<Payment> findAllPaymentByStoreAndBuyerAndStatus(@Param("storeId") Long storeId, @Param("name") String name, @Param("status") Long status);

    //done
    @Query(value = "select p.id, p.date, p.price, p.status, p.delivery_id, p.store_id, p.user_id, p.address_id, p.code \n" +
            "from\n" +
            " store s join payment p on s.id =  p.store_id join user u on p.user_id = u.id \n" +
            "where (s.id= :storeId and u.phone like %:phone%) ", nativeQuery = true)
    Iterable<Payment> findAllPaymentByStoreAndPhone(@Param("storeId") Long storeId, @Param("phone") String phone);

    //done
    @Query(value = "select p.id, p.date, p.price, p.status, p.delivery_id, p.store_id, p.user_id, p.address_id, p.code \n" +
            "from\n" +
            " store s join payment p on s.id =  p.store_id join user u on p.user_id = u.id \n" +
            "where (s.id= :storeId and u.phone like %:phone%) and p.status= :status ", nativeQuery = true)
    Iterable<Payment> findAllPaymentByStoreAndPhoneAndStatus(@Param("storeId") Long storeId, @Param("phone") String phone, @Param("status") Long status);

    //done
    @Query(value = "select p.id, p.date, p.price, p.status, p.delivery_id, p.store_id, p.user_id, p.address_id, p.code \n" +
            "from\n" +
            " store s join payment p on s.id =  p.store_id join user u on p.user_id = u.id \n" +
            "where (s.id= :storeId ) and (p.price between :min and :max) ", nativeQuery = true)
    Iterable<Payment> findAllPaymentByStoreAndPrice(@Param("storeId") Long storeId, @Param("min") double min, @Param("max") double max);

    //done
    @Query(value = "select p.id, p.date, p.price, p.status, p.delivery_id, p.store_id, p.user_id, p.address_id, p.code \n" +
            "from\n" +
            " store s join payment p on s.id =  p.store_id join user u on p.user_id = u.id \n" +
            "where (s.id= :storeId and p.status= :status) and  (p.price between :min and :max) ", nativeQuery = true)
    Iterable<Payment> findAllPaymentByStoreAndPriceAndStatus(@Param("storeId") Long storeId, @Param("status") Long status, @Param("min") double min, @Param("max") double max);

    //done
    @Query(value = "select p.id, p.date, p.price, p.status, p.delivery_id, p.store_id, p.user_id, p.address_id, p.code \n" +
            "from\n" +
            " store s join payment p on s.id =  p.store_id join user u on p.user_id = u.id \n" +
            "where (s.id= :storeId) and  Date(p.date)= :date ", nativeQuery = true)
    Iterable<Payment> findAllPaymentByStoreAndDay(@Param("storeId") Long storeId, @Param("date") String date);

    //done
    @Query(value = "select p.id, p.date, p.price, p.status, p.delivery_id, p.store_id, p.user_id, p.address_id, p.code \n" +
            "from\n" +
            " store s join payment p on s.id =  p.store_id join user u on p.user_id = u.id \n" +
            "where (s.id= :storeId and p.status= :status) and  Date(p.date)= :date ", nativeQuery = true)
    Iterable<Payment> findAllPaymentByStoreAndDayAndStatus(@Param("storeId") Long storeId, @Param("status") Long status, @Param("date") String date);

    //done
    @Query(value = "select p.id, p.date, p.price, p.status, p.delivery_id, p.store_id, p.user_id, p.address_id, p.code \n" +
            "from\n" +
            " store s join payment p on s.id =  p.store_id join user u on p.user_id = u.id \n" +
            "where (s.id= :storeId) and  (Month(p.date)= :month and Year(p.date)= :year ) ", nativeQuery = true)
    Iterable<Payment> findAllPaymentByStoreAndMonth(@Param("storeId") Long storeId, @Param("month") String month, @Param("year") String year);

    //done
    @Query(value = "select p.id, p.date, p.price, p.status, p.delivery_id, p.store_id, p.user_id, p.address_id, p.code \n" +
            "from\n" +
            " store s join payment p on s.id =  p.store_id join user u on p.user_id = u.id \n" +
            "where (s.id= :storeId and p.status= :status) and  (Month(p.date)= :month and Year(p.date)= :year ) ", nativeQuery = true)
    Iterable<Payment> findAllPaymentByStoreAndMonthAndStatus(@Param("storeId") Long storeId, @Param("status") Long status, @Param("month") String month, @Param("year") String year);

    @Query(value = "select p.id, p.date, p.price, p.status, p.delivery_id, p.store_id, p.user_id, p.address_id, p.code \n" +
            "from\n" +
            " payment p join store s on p.store_id = s.id \n" +
            "where s.id= :storeId and p.status= :status ", nativeQuery = true)
    Iterable<Payment> findAllPaymentByStoreAndStatus(@Param("storeId") Long storeId, @Param("status") Long status);
}
