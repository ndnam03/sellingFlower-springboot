package com.vnstart.library.service.impl;

import com.vnstart.library.model.*;
import com.vnstart.library.repository.CartItemRepository;
import com.vnstart.library.repository.OrderDetailRepository;
import com.vnstart.library.repository.OrderRepository;
import com.vnstart.library.repository.ShoppingCartRepository;
import com.vnstart.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public Order saveOrder(ShoppingCart cart) {
        Order order = new Order();
        order.setOrderStatus("PENDING");
        order.setOrderDate(new Date());
        order.setCustomer(cart.getCustomer());
        order.setTotalPrice(cart.getTotalPrices());
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for(CartItem item : cart.getCartItem()){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setProduct(item.getProduct());
            orderDetail.setUnitPrice(item.getProduct().getCostPrice());
            orderDetailRepository.save(orderDetail);
            orderDetailList.add(orderDetail);
            cartItemRepository.delete(item);
        }
        order.setOrderDetailList(orderDetailList);
        cart.setCartItem(new HashSet<>());
        cart.setTotalPrices((double) 0);
        cart.setTotalPrices((double) 0);
        shoppingCartRepository.save(cart);
        orderRepository.save(order);

        return order;
    }

    @Override
    public void acceptOrder(Long id) {
        Order order = orderRepository.getById(id);
        order.setDeliveryDate(new Date());
        order.setOrderStatus("SHIPPING");
        orderRepository.save(order);
    }

    @Override
    public void canceOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void successOrder(Long id) {
        Order order = orderRepository.getById(id);;
        order.setOrderStatus("SUCCESS");
        orderRepository.save(order);
    }

    @Override
    public List<Product> getProductsByOrderId(Long orderId) {
        return orderRepository.findProductsByOrderId(orderId);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public  Customer findByCustomerAndId(Long id) {
        return orderRepository.findByCustomerAndId(id);
    }


}
