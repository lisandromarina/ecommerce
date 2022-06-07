package com.ecommerce.DTO;

import javax.persistence.Column;

public class ProductDTO {

    private Long id;

    private String name;

    private Double price;

    private String description;

    private Boolean isActive;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, Double price, String description, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.isActive = isActive;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
