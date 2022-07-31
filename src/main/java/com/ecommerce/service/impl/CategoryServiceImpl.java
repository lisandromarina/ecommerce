package com.ecommerce.service.impl;

import com.ecommerce.DTO.CategoryDTO;
import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.AbmService;
import com.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public Category save(CategoryDTO categoryDTO) {
        validateFieldsCategory(categoryDTO);

        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setActive(true);

        try {
            return categoryRepository.save(category);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    @Override
    public List<CategoryDTO> findAll() {
        try {
            return categoryRepository.findAllDTO();
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    @Override
    public void update(CategoryDTO objectDTO) {

    }

    @Override
    public CategoryDTO findById(Long id) {
        validateCategoryId(id);
        try {
            return categoryRepository.findCategoryById(id);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) {
        validateCategoryId(id);
        try {
            productRepository.changeCategoryInAllProductByCategoryId(id);
            categoryRepository.invalidateCategoryById(id);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    private void validateFieldsCategory(CategoryDTO categoryDTO) {
        if (categoryDTO.getName() == null || categoryDTO.getName().isEmpty()) {
            throw new ApiRequestException("The category cannot have empty fields", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateCategoryId(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ApiRequestException("The category with id " + categoryId + " doesn't exist", HttpStatus.NOT_FOUND);
        }
    }
}
