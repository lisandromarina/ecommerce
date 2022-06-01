package com.ecommerce.controller;

import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/findAll")
    public List<ProductDTO> findAllProduct() {
        return productService.findAll();
    }

    @GetMapping("/findById/{id}")
    public ProductDTO findProductById(@PathVariable("id") Long id) {
        return productService.findById(id);
    }

    @PostMapping("/save")
    public void saveProduct(@RequestBody ProductDTO productDto) {
        productService.save(productDto);
    }

    @PutMapping("/update")
    public void updateProduct(@RequestBody ProductDTO productDTO) {
        productService.update(productDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProductById(@PathVariable("id") Long id) {
        productService.delete(id);
    }
}
