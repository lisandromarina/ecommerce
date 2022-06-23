package com.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String name;

    @Column(name = "product_price", nullable = false )
    private Double price;

    @Column(name = "product_description")
    private String description;

    @Column(name = "product_is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
