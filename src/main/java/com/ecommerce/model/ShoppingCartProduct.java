package com.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "shopping_cart_product")
public class ShoppingCartProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ShoppingCartProductPK pk;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double sellPrice;

    public ShoppingCartProduct() {
    }

    public ShoppingCartProduct(ShoppingCart shoppingCart, Product product, Integer quantity) {
        pk = new ShoppingCartProductPK();
        pk.setOrder(shoppingCart);
        pk.setProduct(product);
        this.quantity = quantity;
    }

    public ShoppingCartProductPK getPk() {
        return pk;
    }

    public void setPk(ShoppingCartProductPK pk) {
        this.pk = pk;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCartProduct that = (ShoppingCartProduct) o;
        return Objects.equals(pk, that.pk) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, quantity);
    }


}
