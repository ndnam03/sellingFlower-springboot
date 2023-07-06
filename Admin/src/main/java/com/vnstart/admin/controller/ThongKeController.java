package com.vnstart.admin.controller;

import com.vnstart.library.model.Order;
import com.vnstart.library.model.Product;
import com.vnstart.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ThongKeController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/thong-ke-so-luong")
    public String thongKeSoLuong(Model model) {
        List<Order> orders = orderService.getAll();

        Map<String, Integer> orderCountByMonth = new HashMap<>();
        for (Order order : orders) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(order.getOrderDate());
            int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0, cần +1 để lấy tháng đúng

            // Kiểm tra xem đã có dữ liệu cho tháng đó trong map hay chưa
            if (orderCountByMonth.containsKey(String.valueOf(month))) {
                // Nếu có, tăng giá trị lên 1
                Integer count = orderCountByMonth.get(String.valueOf(month));
                orderCountByMonth.put(String.valueOf(month), count + 1);
            } else {
                // Nếu chưa, thêm dữ liệu mới cho tháng đó với giá trị ban đầu là 1
                orderCountByMonth.put(String.valueOf(month), 1);
            }
        }
        model.addAttribute("orderCountByMonth", orderCountByMonth);
        return "thong-ke-so-luong";
    }
    @GetMapping("/thong-ke-so-tien")
    public String thongKeSoTien(Model model) {
        List<Order> orders = orderService.getAll();

        Map<String, Double> totalAmountByMonth = new HashMap<>();
        for (Order order : orders) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(order.getOrderDate());
            int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0, cần +1 để lấy tháng đúng

            // Kiểm tra xem đã có dữ liệu cho tháng đó trong map hay chưa
            if (totalAmountByMonth.containsKey(String.valueOf(month))) {
                // Nếu có, cộng thêm số tiền của đơn hàng vào tổng số tiền của tháng
                Double totalAmount = totalAmountByMonth.get(String.valueOf(month));
                totalAmount += order.getTotalPrice();
                totalAmountByMonth.put(String.valueOf(month), totalAmount);
            } else {
                // Nếu chưa, thêm dữ liệu mới cho tháng đó với giá trị là số tiền của đơn hàng
                totalAmountByMonth.put(String.valueOf(month), order.getTotalPrice());
            }
        }
        model.addAttribute("totalAmountByMonth", totalAmountByMonth);
        return "thong-ke-so-tien";
    }

    @GetMapping("/thong-ke-so-luong-theo-thang")
    public String thongKeSoLuongTheoThang(Model model) {
        List<Order> orders = orderService.getAll();

        Map<String, Integer> productCountByMonth = new HashMap<>();
        for (Order order : orders) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(order.getOrderDate());
            int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0, cần +1 để lấy tháng đúng

            List<Product> products = order.getProducts();
            int productCount = products.size();

            // Kiểm tra xem đã có dữ liệu cho tháng đó trong map hay chưa
            if (productCountByMonth.containsKey(String.valueOf(month))) {
                // Nếu có, cộng dồn số lượng sản phẩm
                int count = productCountByMonth.get(String.valueOf(month));
                productCountByMonth.put(String.valueOf(month), count + productCount);
            } else {
                // Nếu chưa, thêm dữ liệu mới cho tháng đó với số lượng sản phẩm ban đầu
                productCountByMonth.put(String.valueOf(month), productCount);
            }
        }
        model.addAttribute("productCountByMonth", productCountByMonth);
        return "thong-ke-so-luong-theo-thang";
    }
}
