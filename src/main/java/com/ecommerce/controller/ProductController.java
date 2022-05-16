package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/findAll")
    public List<Product> findAllProduct() {
        return productService.findAllProducts();
    }

    @GetMapping("/findById/{id}")
    public Product findProductById(@PathVariable("id") Long id) {
        return productService.findProductById(id);
    }

    @PostMapping("/save")
    public void saveProduct(@RequestBody Product product) {
        productService.saveProduct(product);
    }

    @PutMapping("/update")
    public void updateProduct(@RequestBody Product product) {
        productService.updateProduct(product);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProductById(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
    }
}
