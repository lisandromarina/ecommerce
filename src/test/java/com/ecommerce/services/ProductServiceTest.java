package com.ecommerce.services;

import com.ecommerce.DTO.CategoryDTO;
import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.controller.ProductController;
import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    @Disabled
    public void createProductWithCategorySuccessTest(){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setActive(true);
        productDTO.setName("oneProduct");
        productDTO.setDescription("oneDescription");
        productDTO.setUserId(1L);
        productDTO.setCategoryDTO(new CategoryDTO(1L, "oneCategory", true));
        productDTO.setPrice(0.8);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(new Category()));
        when(productRepository.save(any(Product.class))).thenReturn(new Product());

        Product product = productService.save(productDTO);
        assertNotNull(product);
    }

    @Test
    @Disabled
    public void createProductWithoutCategorySuccessTest(){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setActive(true);
        productDTO.setName("oneProduct");
        productDTO.setDescription("oneDescription");
        productDTO.setUserId(1L);
        productDTO.setCategoryDTO(new CategoryDTO(1L, "oneCategory", true));
        productDTO.setPrice(0.8);

        Optional<Category> categoryOptional = Optional.ofNullable(null);

        when(categoryRepository.findById(anyLong())).thenReturn(categoryOptional);
        when(productRepository.save(any(Product.class))).thenReturn(new Product());

        Product product = productService.save(productDTO);

        assertFalse(categoryOptional.isPresent());
        assertNotNull(product);
    }

    @Test
    public void createProductWithoutNameTest(){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setActive(true);
        productDTO.setDescription("oneDescription");
        productDTO.setUserId(1L);
        productDTO.setCategoryDTO(new CategoryDTO(1L, "oneCategory", true));
        productDTO.setPrice(0.8);

        ApiRequestException thrown = assertThrows(ApiRequestException.class,() -> productService.save(productDTO),
                "Expected porduct name empty to throw exception");

        assertTrue(thrown.getMessage().contains("The product cannot have empty fields"));
    }

    @Test
    public void createProductWithoutDescriptionTest(){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setActive(true);
        productDTO.setName("oneName");
        productDTO.setUserId(1L);
        productDTO.setCategoryDTO(new CategoryDTO(1L, "oneCategory", true));
        productDTO.setPrice(0.8);

        ApiRequestException thrown = assertThrows(ApiRequestException.class,() -> productService.save(productDTO),
                "Expected porduct description empty to throw exception");

        assertTrue(thrown.getMessage().contains("The product cannot have empty fields"));
    }

    @Test
    public void createProductWithoutUserIdTest(){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setActive(true);
        productDTO.setName("oneName");
        productDTO.setDescription("oneDescription");
        productDTO.setCategoryDTO(new CategoryDTO(1L, "oneCategory", true));
        productDTO.setPrice(0.8);

        ApiRequestException thrown = assertThrows(ApiRequestException.class,() -> productService.save(productDTO),
                "Expected userId description empty to throw exception");

        assertTrue(thrown.getMessage().contains("The product cannot have empty fields"));
    }

    @Test
    public void createProductWithoutPriceTest(){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setActive(true);
        productDTO.setName("oneName");
        productDTO.setDescription("oneDescription");
        productDTO.setUserId(1L);
        productDTO.setCategoryDTO(new CategoryDTO(1L, "oneCategory", true));

        ApiRequestException thrown = assertThrows(ApiRequestException.class,() -> productService.save(productDTO),
                "Expected porduct price empty to throw exception");

        assertTrue(thrown.getMessage().contains("The product cannot have empty fields"));
    }

    @Test
    public void findAllProductTest(){
        List<ProductDTO> allProducts = new ArrayList<>();
        allProducts.add(new ProductDTO());
        allProducts.add(new ProductDTO());

        when(productRepository.findAllDTO()).thenReturn(allProducts);

        List<ProductDTO> result = productService.findAll();

        assertTrue(result.size() == 2);
        assertEquals(result, allProducts, "the lists must be the same");
    }

    @Test
    @Disabled
    public void findProductByIdSuccessTest(){
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);

        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.findProductDTOById(productId)).thenReturn(productDTO);

        ProductDTO result = productService.findById(productId);

        assertEquals(result, productDTO, "the products must be the same");
        assertTrue(productDTO.getId() == productId);
    }

    @Test
    public void findProductByIdNotIdExistTest(){
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(false);

        ApiRequestException thrown = assertThrows(ApiRequestException.class,() -> productService.findById(productId),
                "Expected not exists id to throw exception");

        assertTrue(thrown.getMessage().contains("The product with id: " + productId + " doesn't exist"));
    }

    @Test
    public void deleteProductNotIdExistTest(){
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(false);

        ApiRequestException thrown = assertThrows(ApiRequestException.class,() -> productService.delete(productId),
                "Expected not exists id to throw exception");

        assertTrue(thrown.getMessage().contains("The product with id: " + productId + " doesn't exist"));
    }

    @Test
    public void deleteProductSuccess(){
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(true);

        productService.delete(productId);

        verify(productRepository, times(1)).invalidateProductById(productId);
    }


}
