package com.ecommerce.services;

import com.ecommerce.DTO.CategoryDTO;
import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    public void createCategorySuccessTest(){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("oneCategory");

        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setActive(true);

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category createdCategory = categoryService.save(categoryDTO);

        assertNotNull(createdCategory);
        assertEquals("oneCategory", createdCategory.getName());
        assertTrue(createdCategory.getActive());
    }

    @Test
    public void createEmptyNameCategoryExceptionTest(){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("");

        ApiRequestException thrown = assertThrows(ApiRequestException.class,() -> categoryService.save(categoryDTO),
                "Expected empty field name to throw exception");

        assertTrue(thrown.getMessage().contains("The category cannot have empty fields"));
    }

    @Test
    public void createNullNameCategoryExceptionTest(){
        CategoryDTO categoryDTO = new CategoryDTO();

        ApiRequestException thrown = assertThrows(ApiRequestException.class,() -> categoryService.save(categoryDTO),
                "Expected empty null field name to throw exception");

        assertTrue(thrown.getMessage().contains("The category cannot have empty fields"));
    }

    @Test
    public void findAllCategorySuccessTest(){
        List<CategoryDTO> listDTO = new ArrayList<>();
        listDTO.add(new CategoryDTO());
        listDTO.add(new CategoryDTO());

        when(categoryRepository.findAllDTO()).thenReturn(listDTO);

        List<CategoryDTO> result = categoryService.findAll();

        assertNotNull(result);
        assertTrue(result.size() == 2);
    }

    @Test
    public void findCategoryByIdSuccessTest(){
        Long categoryID = 1L;
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(categoryID);

        when(categoryRepository.findCategoryById(categoryID)).thenReturn(categoryDTO);
        when(categoryRepository.existsById(categoryID)).thenReturn(true);

        CategoryDTO result = categoryService.findById(categoryID);

        assertNotNull(result);
        assertEquals(categoryID, result.getId());
    }

    @Test
    public void findCategoryByIdNotExistTest(){
        Long categoryID = 1L;

        when(categoryRepository.existsById(categoryID)).thenReturn(false);

        ApiRequestException thrown = assertThrows(ApiRequestException.class,() -> categoryService.findById(categoryID),
                "Expected category id different to throw exception");

        assertTrue(thrown.getMessage().contains("The category with id " + categoryID + " doesn't exist"));
    }

    @Test
    public void findCategoryByIdNullTest(){
        Long categoryID = null;

        when(categoryRepository.existsById(categoryID)).thenReturn(false);

        ApiRequestException thrown = assertThrows(ApiRequestException.class,() -> categoryService.findById(categoryID),
                "Expected category id different to throw exception");

        assertTrue(thrown.getMessage().contains("The category with id " + categoryID + " doesn't exist"));
    }

    @Test
    public void deleteIdNotExistTest(){
        Long categoryID = null;

        when(categoryRepository.existsById(categoryID)).thenReturn(false);

        ApiRequestException thrown = assertThrows(ApiRequestException.class,() -> categoryService.delete(categoryID),
                "Expected category id different to throw exception");

        assertTrue(thrown.getMessage().contains("The category with id " + categoryID + " doesn't exist"));
    }

    @Test
    public void deleteIdNullTest(){
        Long categoryID = 1L;

        when(categoryRepository.existsById(categoryID)).thenReturn(false);

        ApiRequestException thrown = assertThrows(ApiRequestException.class,() -> categoryService.delete(categoryID),
                "Expected category id different to throw exception");

        assertTrue(thrown.getMessage().contains("The category with id " + categoryID + " doesn't exist"));
    }

    @Test
    public void deleteSuccessTest(){
        Long categoryID = 1L;

        when(categoryRepository.existsById(categoryID)).thenReturn(true);

        categoryService.delete(categoryID);

        verify(productRepository, times(1)).changeCategoryInAllProductByCategoryId(categoryID);
        verify(categoryRepository, times(1)).invalidateCategoryById(categoryID);
    }
}
