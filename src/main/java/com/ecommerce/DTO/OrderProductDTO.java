package com.ecommerce.DTO;

import com.ecommerce.model.Order;
import com.ecommerce.model.OrderProduct;
import com.ecommerce.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrderProductDTO {
    private Product product;
    private Double sellPrice;

    private Integer quantity;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public OrderProductDTO() {
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public OrderProductDTO(Long productId, String name, Double price,
                           Double sellPrice, Integer quantity) {
        this.product = new Product();
        this.product.setName(name);
        this.product.setId(productId);
        this.product.setPrice(price);
        this.sellPrice = sellPrice;
        this.quantity = quantity;
    }
}
