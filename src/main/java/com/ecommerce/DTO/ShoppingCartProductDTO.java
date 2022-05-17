package com.ecommerce.DTO;

public class ShoppingCartProductDTO {
    private ProductDTO productDTO;
    private Double sellPrice;

    private Integer quantity;

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
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

    public ShoppingCartProductDTO(Long productId, String name, Double price,
                                  Double sellPrice, Integer quantity) {
        this.productDTO = new ProductDTO();
        this.productDTO.setName(name);
        this.productDTO.setId(productId);
        this.productDTO.setPrice(price);
        this.sellPrice = sellPrice;
        this.quantity = quantity;
    }
}
