package com.vnstart.admin.controller;


import com.vnstart.library.model.Customer;
import com.vnstart.library.model.Order;
import com.vnstart.library.model.Product;
import com.vnstart.library.service.CustomerService;
import com.vnstart.library.service.EmailService;
import com.vnstart.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import java.security.Principal;
import java.util.List;

@Controller
public class OrderManagementController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("/order-management")
    public String orderManagement(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<Order> orders = orderService.getAll();
        model.addAttribute("orders", orders);
        return "order-management";
    }

    @GetMapping("/delivery/{id}")
    public String updateStatus(@PathVariable("id") Long id, Model model, Principal principal, RedirectAttributes attributes) {
        if (principal == null) {
            return "redirect:/login";
        }
        orderService.acceptOrder(id);

        return "redirect:/order-management";
    }

    @GetMapping("/delete/{id}")
    public String deletedOrder(@PathVariable("id") Long id, Model model, Principal principal, RedirectAttributes attributes) {
        if (principal == null) {
            return "redirect:/login";
        }
        orderService.canceOrder(id);

        return "redirect:/order-management";
    }

    @GetMapping("/success/{id}")
    public String successOrder(@PathVariable("id") Long id, Model model, Principal principal, RedirectAttributes attributes) throws MessagingException {
        if (principal == null) {
            return "redirect:/login";
        }
        orderService.successOrder(id);
        // Gửi email thông báo đến khách hàng
        Customer customer = orderService.findByCustomerAndId(id);
        String to = customer.getUsername();
        List<Order> orders = customer.getOrders();
        String subject = "Xác nhận đơn hàng #" + id + " đã được xử lý thành công";

// Lấy danh sách sản phẩm từ mỗi đơn hàng
        StringBuilder textBuilder = new StringBuilder();
        for (Order order : orders) {
            if (order.getId().equals(id)) {
                List<Product> products = order.getProducts();
                textBuilder.append("Sản phẩm:\n");
                for (Product product : products) {
                    textBuilder.append("- ");
                    textBuilder.append(product.getName());
                    textBuilder.append("\n");
                }
            }
        }

        String text = "Chào bạn,\n\nĐơn hàng #" + id + "\n\n" + textBuilder.toString() + " của bạn đã được xử lý thành công.\n\nCảm ơn bạn đã sử dụng dịch vụ của chúng tôi!";

        sendEmail(to, subject, text);
        return "redirect:/order-management";
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-email");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
