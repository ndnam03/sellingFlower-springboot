package com.vnstart.admin.controller;


import com.vnstart.library.model.Category;
import com.vnstart.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String categories(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("size", categories.size());
        model.addAttribute("title", "Category");
        model.addAttribute("categoryNew", new Category());
        return "categories";
    }

    @PostMapping("/add-category")
    public String addCategory(@ModelAttribute("categoryNew") Category category, RedirectAttributes attributes) {
        try {
            categoryService.save(category);
            attributes.addFlashAttribute("success", "Added successfully");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to add because duplicate name");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/findById", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Category findById(Long id) {
        return categoryService.findById(id);
    }

    @GetMapping("/update-category")
    public String updateCategory(Category category, RedirectAttributes attributes) {

        try {
            categoryService.update(category);
            attributes.addFlashAttribute("success", "Update thành công");
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Category đã tồn tại");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Error server");
        }

        return "redirect:/categories";
    }

    @RequestMapping(value = "/delete-category", method = {RequestMethod.PUT,RequestMethod.GET})
    public String deleteCategory(Long id, RedirectAttributes attributes){

        try {
            categoryService.deleteById(id);
            attributes.addFlashAttribute("success","Deleted successfully");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed","Failed to delete");
        }

        return "redirect:/categories";
    }

    @RequestMapping(value = "/enable-category", method = {RequestMethod.PUT,RequestMethod.GET})
    public String enabledCategory(Long id, RedirectAttributes attributes){

        try {
            categoryService.enabled(id);
            attributes.addFlashAttribute("success","Enabled successfully");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed","Failed to Enabled");
        }

        return "redirect:/categories";
    }
}

