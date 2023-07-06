package com.vnstart.customer.controller;

import com.vnstart.library.dto.ProductDto;
import com.vnstart.library.model.Category;
import com.vnstart.library.model.Product;
import com.vnstart.library.service.CategoryService;
import com.vnstart.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @GetMapping("/products/{pageNo}")
    public String products(Model model, @PathVariable("pageNo") int pageNo) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        Page<ProductDto> products = productService.pageProducts(pageNo);
        model.addAttribute("products", products);
        model.addAttribute("size", products.getSize());
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        return "products";
    }

    @GetMapping("products-in-category/{id}")
    public String findProductsByCategoryId(@PathVariable("id") Long id, Model model) {
        Page<Product> products = productService.findProductsByCategoryID(id);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("categoryId", id);
        model.addAttribute("size", products.getSize());
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("currentPage", 0);

        return "productsByCId";
    }

    @GetMapping("/products/search/costPrice/{pageNo}")
    public String searchProductsByCostPriceRange(@RequestParam("min") Double min,
                                                 @RequestParam("max") Double max, Model model,
                                                 @PathVariable("pageNo") int pageNo) {

        Page<ProductDto> products = productService.findProductsByCostPrices(min, max, pageNo);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("size", products.getSize());
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        return "productsByCostPrice";
    }

    @GetMapping(value = {"/", "index"})
    public String home(Model model) {
        List<Product> products = productService.findTop4ProductsBest();
        model.addAttribute("productsBest", products);
        List<Product> top4new = productService.findTop4ProductsNew();
        model.addAttribute("productsNew", top4new);

        return "index";
    }

    @GetMapping("/find-product/{id}")
    public String findProductById(Model model, @PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        List<Category> categories = categoryService.findAll();
        List<Product> radom4ProductByCid = productService.findRandomProductsByCategoryId(product.getCategory().getId());
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        model.addAttribute("productsRandom", radom4ProductByCid);
        System.out.println(radom4ProductByCid.size());

        return "shop-details";
    }
    @PostMapping("/search-product/{pageNo}")
    public String searchProducts(Model model,
                                 @PathVariable("pageNo")int pageNo
            , @RequestParam("keyword")String keyword){

        Page<ProductDto> products = productService.searchProducts(pageNo,keyword);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("size",products.getSize());
        model.addAttribute("totalPages",products.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        return "result-product";
    }

}
