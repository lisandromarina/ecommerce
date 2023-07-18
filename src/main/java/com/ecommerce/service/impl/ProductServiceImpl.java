package com.ecommerce.service.impl;

import com.ecommerce.DTO.CommentDTO;
import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.CommentRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ImageKitServiceImpl imageKitService;
    @Autowired
    CommentRepository commentRepository;
    @Override
    public Product save(ProductDTO productDTO) {
        try {
            validateInvalidProductFields(productDTO);

            Product product = new Product();
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setUserId(productDTO.getUserId());
            product.setDescription(productDTO.getDescription());
            product.setActive(Boolean.TRUE);

            Category category = null;
            if(productDTO.getCategoryId() != null){
                category = getCategoryById(productDTO.getCategoryId());
            }
            product.setCategory(category);

            String imageUrl = uploadImages(productDTO.getFile());
            product.setImageUrl(imageUrl);
            return productRepository.save(product);

        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    private String uploadImages(MultipartFile file){
        try {
            return imageKitService.uploadImage(file.getBytes(), file.getOriginalFilename());
        }catch (Exception e){
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
            ProductDTO productDTO = productRepository.findProductDTOById(id);
            List<CommentDTO> comments = commentRepository.findCommentsByIProductId(id);
            productDTO.setComments(comments);
            return productDTO;
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    @Override
    public List<ProductDTO> findProductByIdCategory(Long idCategory) {
        return productRepository.findProductByIdCategory(idCategory);
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

    @Override
    public List<ProductDTO> findBySearchTool(String inputText) {
        System.out.println(inputText);
        Pageable pageable = PageRequest.of(0, 7);
        return productRepository.findProductNamesContainingInput(inputText, pageable);
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
