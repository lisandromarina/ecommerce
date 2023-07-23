package com.ecommerce.services;

import com.ecommerce.DTO.CategoryDTO;
import com.ecommerce.DTO.CommentDTO;
import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.controller.ProductController;
import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.CommentRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.impl.ImageKitServiceImpl;
import com.ecommerce.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

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
    private CommentRepository commentRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ImageKitServiceImpl imageKitService;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void testSaveProductWithValidCategory() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("oneName");
        productDTO.setPrice(100.0);
        productDTO.setUserId(1L);
        productDTO.setDescription("oneDescription");
        productDTO.setCategoryId(1L); // Assuming a valid category ID
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",                // Name of the file attribute
                "test.txt",            // Original file name
                "text/plain",          // Content type
                "Test file content".getBytes()  // File content as byte array
        );
        productDTO.setFile(mockFile);

        Category category = new Category();
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        String imageUrl = "http://example.com/mock-image-url.jpg";
        when(imageKitService.uploadImage(any(byte[].class), anyString())).thenReturn(imageUrl);

        lenient().when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product product = invocation.getArgument(0);
            product.setId(1L);
            product.setName("oneName");
            product.setPrice(100.0);
            return product;
        });
        Product savedProduct = productService.save(productDTO);

        verify(productRepository, atLeastOnce()).save(any(Product.class));
        assertNotNull(savedProduct);
        assertEquals(productDTO.getName(), savedProduct.getName());
        assertEquals(productDTO.getPrice(), savedProduct.getPrice());
        assertEquals(productDTO.getUserId(), savedProduct.getUserId());
        assertEquals(productDTO.getDescription(), savedProduct.getDescription());
    }
    @Test
    public void createProductWithoutCategorySuccessTest(){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("oneName");
        productDTO.setPrice(100.0);
        productDTO.setUserId(1L);
        productDTO.setDescription("oneDescription");
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",                // Name of the file attribute
                "test.txt",            // Original file name
                "text/plain",          // Content type
                "Test file content".getBytes()  // File content as byte array
        );
        productDTO.setFile(mockFile);

        String imageUrl = "http://example.com/mock-image-url.jpg";
        when(imageKitService.uploadImage(any(byte[].class), anyString())).thenReturn(imageUrl);

        lenient().when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product product = invocation.getArgument(0);
            product.setId(1L);
            product.setName("oneName");
            product.setPrice(100.0);
            return product;
        });
        Product savedProduct = productService.save(productDTO);

        verify(productRepository, atLeastOnce()).save(any(Product.class));
        assertNotNull(savedProduct);
        assertEquals(productDTO.getName(), savedProduct.getName());
        assertEquals(productDTO.getPrice(), savedProduct.getPrice());
        assertEquals(productDTO.getUserId(), savedProduct.getUserId());
        assertEquals(productDTO.getDescription(), savedProduct.getDescription());
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
    public void findProductByIdSuccessTest(){
        Long productId = 1L;
        List<CommentDTO> listComments = new ArrayList<>();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);

        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.findProductDTOById(productId)).thenReturn(productDTO);
        when(commentRepository.findCommentsByIProductId(productId)).thenReturn(listComments);

        ProductDTO result = productService.findById(productId);

        assertEquals(result, productDTO, "the products must be the same");
        assertTrue(productDTO.getId() == productId);
        assertTrue(productDTO.getComments() == listComments);
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
