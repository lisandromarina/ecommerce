package com.ecommerce.controller;

import com.ecommerce.DTO.CategoryDTO;
import com.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/findAll")
    public List<CategoryDTO> findAllCategory() {
        return categoryService.findAll();
    }

    @GetMapping("/findById/{id}")
    public CategoryDTO findCategoryById(@PathVariable("id") Long id) {
        return categoryService.findById(id);
    }

    @PostMapping("/save")
    public void saveCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.save(categoryDTO);
    }

    @PutMapping("/update")
    public void updateCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.update(categoryDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategoryById(@PathVariable("id") Long id) {
        categoryService.delete(id);
    }
}
