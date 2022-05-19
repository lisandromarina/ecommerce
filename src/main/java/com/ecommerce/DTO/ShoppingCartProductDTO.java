package com.ecommerce.DTO;

public class ShoppingCartProductDTO {
    private Long idProduct;
    private Long idShoppingCart;
    private Double sellPrice;
    private String nameProduct;
    private Integer quantity;

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public Long getIdShoppingCart() {
        return idShoppingCart;
    }

    public void setIdShoppingCart(Long idShoppingCart) {
        this.idShoppingCart = idShoppingCart;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public ShoppingCartProductDTO(Long productId, String name, Double sellPrice, Integer quantity) {
        this.nameProduct = name;
        this.idProduct = productId;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
    }
}
