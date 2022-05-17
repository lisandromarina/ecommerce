package com.ecommerce.DTO;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class OrderDTO {
    private Long id;

    private LocalDate dateCreated;

    private Boolean status;

    private Set<OrderProductDTO> orderProductsDTO = new HashSet<>();

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

    public Set<OrderProductDTO> getOrderProductsDTO() {
        return orderProductsDTO;
    }

    public void setOrderProductsDTO(Set<OrderProductDTO> orderProducts) {
        this.orderProductsDTO = orderProducts;
    }

    public OrderDTO(Long id, LocalDate dateCreated, Boolean status) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.status = status;
    }
}
