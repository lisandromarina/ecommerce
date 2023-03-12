package com.ecommerce.DTO;

import com.ecommerce.model.Category;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;

public class ProductDTO {

    private Long id;

    private Long userId;

    private String name;

    private Double price;

    private String description;

    private Boolean isActive;

    private  MultipartFile file;

    private String imageUrl;

    private CategoryDTO categoryDTO;

    private Long categoryId;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, Double price, String description, Boolean isActive,
                      Long categoryId,String categoryName,Boolean catoryIsActive, Long userId, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.isActive = isActive;
        this.categoryDTO = new CategoryDTO(categoryId, categoryName, catoryIsActive);
        this.userId = userId;
        this.imageUrl = imageUrl;
    }

    public ProductDTO(Long id, String name, Double price, String description, Boolean isActive, Long userId, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.isActive = isActive;
        this.userId = userId;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
