package com.ecommerce.DTO;

import javax.persistence.Column;

public class ProductDTO {

    Long id;

    String name;

    Double price;

    Boolean isActive;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, Double price, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.price = price;
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
