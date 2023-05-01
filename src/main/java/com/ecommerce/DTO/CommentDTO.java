package com.ecommerce.DTO;

import com.ecommerce.model.Product;

import javax.persistence.*;

public class CommentDTO {

    private Long id;
    private String description;
    private UserDTO user;

    private ProductDTO product;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public CommentDTO(Long id, String description, Long userId, Long productId) {
        this.id = id;
        this.description = description;
        this.user =  new UserDTO(userId);;
        this.product = new ProductDTO(productId);
    }

    public CommentDTO(Long id, String description, Long userId, String firstName, String lastName,
                      String email, Long productId, String productName, Double price, String productDescription){
        this.id = id;
        this.description = description;
        this.user = new UserDTO(userId);;
        this.user.setFirstName(firstName);
        this.user.setLastName(lastName);
        this.user.setEmail(email);
        this.product = new ProductDTO(productId);
        this.product.setName(productName);
        this.product.setPrice(price);
        this.product.setDescription(productDescription);
    }
}
