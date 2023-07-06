package com.vnstart.customer.controller;

import com.vnstart.library.dto.CustomerDto;
import com.vnstart.library.model.Customer;
import com.vnstart.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("customerDto", new CustomerDto());
        return "register";
    }

    @PostMapping("do-register")
    public String doRegister(@Valid @ModelAttribute("customerDto") CustomerDto customerDto,
                             BindingResult result, Model model,
                             RedirectAttributes attributes) {

        try {
            if (result.hasErrors()) {
                model.addAttribute("customerDto", customerDto);
                return "register";
            }
            Customer customer = customerService.findByUsername(customerDto.getUsername());
            if (customer != null) {
                model.addAttribute("username", "Username have been registered");
                model.addAttribute("customerDto", customerDto);
                return "register";
            }
            if (customerDto.getPassword().equals(customerDto.getRepeatPassword())) {
                customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));

                CustomerDto customerDtoSave = customerService.save(customerDto);
                model.addAttribute("success", "Register successfully!");
                return "register";
            } else {
                model.addAttribute("password", "Password is not same");
                model.addAttribute("customerDto", customerDto);
                return "register";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Server have ran some problems");
            model.addAttribute("customerDto", customerDto);
        }
        return "register";
    }


}
