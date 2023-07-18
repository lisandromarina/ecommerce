package com.ecommerce.service;

import com.ecommerce.DTO.CategoryDTO;
import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.model.Product;

import java.util.List;

public interface ProductService {
    Product save(ProductDTO objectDTO);

    List<ProductDTO> findAll();

    void update(ProductDTO objectDTO);

    ProductDTO findById(Long id);

    List<ProductDTO> findBySearchTool(String inputText);

    List<ProductDTO> findProductByIdCategory(Long idCategory);
    void delete(Long id);
}
