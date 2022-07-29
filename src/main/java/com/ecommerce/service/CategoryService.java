package com.ecommerce.service;

import com.ecommerce.DTO.CategoryDTO;
import com.ecommerce.model.Category;

import java.util.List;

public interface CategoryService {

    Category save(CategoryDTO objectDTO);

    List<CategoryDTO> findAll();

    void update(CategoryDTO objectDTO);

    CategoryDTO findById(Long id);

    void delete(Long id);
}
