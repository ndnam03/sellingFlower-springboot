package com.vnstart.library.service;

import com.vnstart.library.model.Customer;
import com.vnstart.library.model.Order;
import com.vnstart.library.model.Product;
import com.vnstart.library.model.ShoppingCart;

import java.util.List;

public interface OrderService {
    Order saveOrder(ShoppingCart cart);

    void acceptOrder(Long id);

    void canceOrder(Long id);
    void successOrder(Long id);
    List<Product> getProductsByOrderId(Long orderId);

    List<Order> getAll();

    //admin
    Customer findByCustomerAndId(Long id);



}
