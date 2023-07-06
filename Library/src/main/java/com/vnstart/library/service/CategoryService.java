package com.vnstart.library.service;

import com.vnstart.library.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category>findAll();
    Category save(Category category);

    Category findById(Long id);
    Category update(Category category);
    void deleteById(Long id);
    public void enabled(Long id);

}
