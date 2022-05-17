package com.ecommerce.DTO;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ShoppingCartDTO {
    private Long id;

    private LocalDate dateCreated;

    private Boolean status;

    private Set<ShoppingCartProductDTO> shoppingCartProductsDTO = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<ShoppingCartProductDTO> getShoppingCartProductsDTO() {
        return shoppingCartProductsDTO;
    }

    public void setShoppingCartProductsDTO(Set<ShoppingCartProductDTO> shoppingCartProducts) {
        this.shoppingCartProductsDTO = shoppingCartProducts;
    }

    public ShoppingCartDTO(Long id, LocalDate dateCreated, Boolean status) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.status = status;
    }
}
