package com.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ShoppingCartProductPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToOne(optional = false)
    @JoinColumn(name = "shopping_cart_id")
    @JsonBackReference
    private ShoppingCart shoppingCart;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setOrder(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCartProductPK that = (ShoppingCartProductPK) o;
        return Objects.equals(shoppingCart, that.shoppingCart) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shoppingCart, product);
    }


}