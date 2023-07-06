package com.vnstart.admin.controller;

import com.vnstart.library.dto.AdminDto;
import com.vnstart.library.model.Admin;

import com.vnstart.library.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/login")
    public String loginForm(Model model) {

        return "login";
    }

    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }

    @PostMapping("register-new")
    public String addNewAdmin( Model model,@Valid @ModelAttribute("adminDto")AdminDto adminDto,
                              BindingResult result, HttpSession session){

        try {
            session.removeAttribute("message");
            if(result.hasErrors()){
                model.addAttribute("adminDto",adminDto);
                return "register";
            }
            String username = adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);
            if(admin != null){
                model.addAttribute("adminDto",adminDto);
                model.addAttribute("emailError","Tài khoản đã tồn tại!!!");
                return "register";
            }
            if(!adminDto.getPassword().equals(adminDto.getRepeatPassword())){
                model.addAttribute("adminDto",adminDto);
                model.addAttribute("passwordError","Mật khẩu nhập lại không đúng!!!");
                return "register";
            }else{
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.save(adminDto);

                model.addAttribute("success","Đăng ký thành công");
                model.addAttribute("adminDto",adminDto);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return "register";
    }


}
