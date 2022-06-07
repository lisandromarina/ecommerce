package com.ecommerce.controller;

import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductServiceImpl productServiceImpl;

    @GetMapping("/findAll")
    public List<ProductDTO> findAllProduct() {
        return productServiceImpl.findAll();
    }

    @GetMapping("/findById/{id}")
    public ProductDTO findProductById(@PathVariable("id") Long id) {
        return productServiceImpl.findById(id);
    }

    @PostMapping("/save")
    public void saveProduct(@RequestBody ProductDTO productDto) {
        productServiceImpl.save(productDto);
    }

    @PutMapping("/update")
    public void updateProduct(@RequestBody ProductDTO productDTO) {
        productServiceImpl.update(productDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProductById(@PathVariable("id") Long id) {
        productServiceImpl.delete(id);
    }
}
