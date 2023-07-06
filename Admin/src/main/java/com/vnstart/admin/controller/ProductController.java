package com.vnstart.admin.controller;

import com.vnstart.library.dto.ProductDto;
import com.vnstart.library.model.Category;
import com.vnstart.library.service.CategoryService;
import com.vnstart.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{pageNo}")
    public String products(Model model,@PathVariable("pageNo")int pageNo,Principal principal) {

        if(principal == null){
            return "redirect:/login";
        }
        Page<ProductDto> products = productService.pageProducts(pageNo);
        model.addAttribute("products", products);
        model.addAttribute("size",products.getSize());
        model.addAttribute("totalPages",products.getTotalPages());
        model.addAttribute("currentPage",pageNo);

        return "products";
    }

    @GetMapping("add-product")
    public String products(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", new ProductDto());
        return "add-product";
    }

    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute("productDto") ProductDto productDto,
                              @RequestParam("imageProduct") MultipartFile imageProduct,
                              RedirectAttributes attributes) {

        try {
            productService.save(imageProduct, productDto);
            attributes.addFlashAttribute("success", "Add successfully");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to add!");
        }

        return "redirect:/products/0";
    }

    @GetMapping("/update-product/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        List<Category> categories = categoryService.findAll();

        ProductDto productDto = productService.getById(id);
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", productDto);

        System.out.println(productDto);
        return "update-product";
    }

    @PostMapping("/update-product/{id}")
    public String processUpdate(@PathVariable("id") Long id, Model model,
                                @ModelAttribute("productDto") ProductDto productDto,
                                @RequestParam("imageProduct") MultipartFile imageProduct,
                                RedirectAttributes attributes) {
        try {
            productService.update(imageProduct, productDto);
            attributes.addFlashAttribute("success", "Update successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update");
        }
        return "redirect:/products/0";
    }

    @GetMapping("/deleted-product/{id}")
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes attributes) {
        productService.deleteById(id);
        attributes.addFlashAttribute("success", "Deleted successfully");
        return "redirect:/products/0";
    }

    @GetMapping("/enable-product/{id}")
    public String enableProduct(@PathVariable("id") Long id, RedirectAttributes attributes) {
        productService.enableById(id);
        attributes.addFlashAttribute("success", "Enable successfully");
        return "redirect:/products/0";
    }

    @PostMapping("/search-products/{pageNo}")
    public String searchProducts(Model model,Principal principal,
                                 @PathVariable("pageNo")int pageNo
            ,@RequestParam("keyword")String keyword){
        if(principal == null){
            return "redirect:/login";
        }
        Page<ProductDto> products = productService.searchProducts(pageNo,keyword);
        model.addAttribute("products", products);
        model.addAttribute("size",products.getSize());
        model.addAttribute("totalPages",products.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        return "result-product";
    }

}
