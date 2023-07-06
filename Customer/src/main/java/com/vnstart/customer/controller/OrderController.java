package com.vnstart.customer.controller;

import com.vnstart.library.model.*;
import com.vnstart.library.service.CustomerService;
import com.vnstart.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;


@Controller
public class OrderController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;


    @GetMapping("/check-out")
    public String checkout(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Customer customer = customerService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = customer.getShoppingCart();
        model.addAttribute("shoppingCart", shoppingCart);
        model.addAttribute("customer", customer);
        model.addAttribute("check",shoppingCart.getCartItem().size());
        return "checkout";
    }

    @PostMapping("/check-out")
    public String checkOut(@ModelAttribute("customer") Customer customer, Principal principal, Model model, RedirectAttributes attributes) {
        if (principal == null) {
            return "redirect:/login";
        }
        if (customer.getPhoneNumber().isEmpty() || customer.getAddress().isEmpty()) {
            attributes.addFlashAttribute("error", "Vui lòng nhập đầy đủ thông tin!!!");
            return "redirect:/check-out";
        }
        customerService.saveInfo(customer);

        String username = principal.getName();
        Customer customer1 = customerService.findByUsername(username);
        ShoppingCart cart = customer1.getShoppingCart();
        orderService.saveOrder(cart);
        List<Order> orderList = customer1.getOrders();
        model.addAttribute("orders",orderList);
        return "order";
    }
    @GetMapping("/order")
    public String order(Model model,Principal principal){
        if(principal == null){
            return "redirect:/login";
        }

        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        List<Order> orderList = customer.getOrders();
        model.addAttribute("orders",orderList);


        return "order";
    }
    @GetMapping("/cancel-order/{id}")
    public String cancelOrder(@PathVariable("id") Long id){
        orderService.canceOrder(id);

        return "redirect:/order";
    }


}
