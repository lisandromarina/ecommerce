package com.ecommerce.service.impl;

import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.AbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductService implements AbmService<ProductDTO> {

    @Autowired
    ProductRepository productRepository;

    @Override
    public void save(ProductDTO productDTO) {
        validateInvalidProductFields(productDTO);

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());

        try {
            productRepository.save(product);
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
                    HttpStatus.BAD_REQUEST);
        }
    }

    private void validateInvalidProductFields(ProductDTO productDTO) {
        if (productDTO.getName() == null
                || productDTO.getName().isEmpty()
                || productDTO.getPrice() == null
                || productDTO.getName().isEmpty()) {
            throw new ApiRequestException("The product cannot have empty fields", HttpStatus.BAD_REQUEST);
        }
    }

    ;
}
