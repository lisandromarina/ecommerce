package com.ecommerce.controller;

import com.ecommerce.DTO.CategoryDTO;
import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "https://ecommerce-pdq0.onrender.com", allowCredentials = "true")
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

    @GetMapping("/findProductByIdCategory/{idCategory}")
    public List<ProductDTO> findProductByIdCategory(@PathVariable("idCategory") Long idCategory){
        return productService.findProductByIdCategory(idCategory);
    }

    @PostMapping(path = "/save")
    public void saveProduct(@ModelAttribute ProductDTO productDto) {
            productService.save(productDto);
    }

    @PostMapping(path = "/findProductListBySearchTool")
    public List<ProductDTO> findProductsBySearchTool(@RequestBody String inputText) {
        return productService.findBySearchTool(inputText);
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
