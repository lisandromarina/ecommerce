package com.ecommerce.service.impl;

import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Product save(ProductDTO productDTO) {
        validateInvalidProductFields(productDTO);

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setUserId(productDTO.getUserId());
        product.setDescription(productDTO.getDescription());
        product.setActive(Boolean.TRUE);

        Category category = null;
        if(productDTO.getCategoryDTO() != null){
            category = getCategoryById(productDTO.getCategoryDTO().getId());
        }
        product.setCategory(category);

        try {
            return productRepository.save(product);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    @Override
    public List<ProductDTO> findAll() {
        try {
            return productRepository.findAllDTO();
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    @Override
    public void update(ProductDTO product) {

        //productRepository.save(product);
    }

    @Override
    public ProductDTO findById(Long id) {
        validateProductExist(id);
        try {
            return productRepository.findProductDTOById(id);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long productId) {
        validateProductExist(productId);
        try {
            productRepository.invalidateProductById(productId);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    private void validateProductExist(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ApiRequestException("The product with id: " + id + " doesn't exist",
                    HttpStatus.NOT_FOUND);
        }
    }

    private void validateInvalidProductFields(ProductDTO productDTO) {
        if (productDTO.getName() == null
                || productDTO.getName().isEmpty()
                || productDTO.getDescription() == null
                || productDTO.getDescription().isEmpty()
                || productDTO.getPrice() == null
                || productDTO.getName().isEmpty()
                || productDTO.getUserId() == null){
            throw new ApiRequestException("The product cannot have empty fields", HttpStatus.BAD_REQUEST);
        }
    }

    private Category getCategoryById(Long idCategory) {
        Optional<Category> category;
        try {
            category = categoryRepository.findById(idCategory);
            if (!category.isPresent()) {
                return null;
            }
            return category.get();
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }
}
