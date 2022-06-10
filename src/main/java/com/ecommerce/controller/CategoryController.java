package com.ecommerce.controller;

import com.ecommerce.DTO.CategoryDTO;
import com.ecommerce.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryServiceImpl categoryServiceImpl;

    @GetMapping("/findAll")
    public List<CategoryDTO> findAllCategory() {
        return categoryServiceImpl.findAll();
    }

    @GetMapping("/findById/{id}")
    public CategoryDTO findCategoryById(@PathVariable("id") Long id) {
        return categoryServiceImpl.findById(id);
    }

    @PostMapping("/save")
    public void saveCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryServiceImpl.save(categoryDTO);
    }

    @PutMapping("/update")
    public void updateCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryServiceImpl.update(categoryDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategoryById(@PathVariable("id") Long id) {
        categoryServiceImpl.delete(id);
    }
}
