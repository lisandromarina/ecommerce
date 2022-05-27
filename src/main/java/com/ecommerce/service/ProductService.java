package com.ecommerce.service;

import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
            Product product = new Product();
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());

            productRepository.save(product);
    }

    @Override
    public List<ProductDTO> findAll() {
        return productRepository.findAllDTO();
    }

    @Override
    public void update(ProductDTO product) {

        //productRepository.save(product);
    }

    @Override
    public ProductDTO findById(Long id) {
        return productRepository.findProductDTOById(id);
    }

    @Override
    public void delete(Long productId) {
        productRepository.deleteProductById(productId);
    }
}
