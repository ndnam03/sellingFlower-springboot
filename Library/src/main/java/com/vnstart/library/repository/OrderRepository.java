package com.vnstart.library.repository;

import com.vnstart.library.model.Customer;
import com.vnstart.library.model.Order;
import com.vnstart.library.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("SELECT od.product FROM OrderDetail od WHERE od.order.id = :orderId")
    List<Product> findProductsByOrderId(@Param("orderId") Long orderId);

    @Query(value = "select o.customer from Order o where o.id = ?1")
    Customer findByCustomerAndId(Long id);

}
