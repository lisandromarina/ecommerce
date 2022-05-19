package com.ecommerce.DTO;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ShoppingCartDTO {
    private Long id;
    private LocalDate dateCreated;
    private Set<ShoppingCartProductDTO> shoppingCartProductsDTO = new HashSet<>();

    public ShoppingCartDTO() {
    }

    public ShoppingCartDTO(Long id, LocalDate dateCreated) {
        this.id = id;
        this.dateCreated = dateCreated;
    }

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

    public Set<ShoppingCartProductDTO> getShoppingCartProductsDTO() {
        return shoppingCartProductsDTO;
    }

    public void setShoppingCartProductsDTO(Set<ShoppingCartProductDTO> shoppingCartProducts) {
        this.shoppingCartProductsDTO = shoppingCartProducts;
    }
}
