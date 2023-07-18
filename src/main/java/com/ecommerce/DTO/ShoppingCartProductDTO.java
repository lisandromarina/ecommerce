package com.ecommerce.DTO;

public class ShoppingCartProductDTO {
    private Long idProduct;
    private Long idShoppingCart;

    public String imageUrl;
    private Double sellPrice;
    private String nameProduct;
    private Integer quantity;

    private Long userId;

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

    public Long getUserId() {
        return userId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getTotal(){
        return this.sellPrice * this.quantity;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ShoppingCartProductDTO(Long productId, String name, Double sellPrice, Integer quantity, String imageUrl) {
        this.nameProduct = name;
        this.idProduct = productId;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }
}
