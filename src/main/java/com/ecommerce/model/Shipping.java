package com.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "shipping")
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shipping_id", nullable = false, updatable = false)
    private Long id;
    @Column(name = "shipping_type")
    private String type;
    @Column(name = "shipping_cost")
    private Double cost;
    @Column(name = "shipping_postal_code", nullable = false)
    private String postalCode;
    @Column(name = "shipping_street", nullable = false)
    private String street;
    @Column(name = "shipping_street_number", nullable = false)
    private String streetNumber;
    @Column(name = "shipping_location", nullable = false)
    private String location;
    @Column(name = "shipping_province", nullable = false)
    private String province;
    @Column(name = "shipping_country")
    private String country;
    @Column(name = "shipping_department")
    private String department;
    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;
    @ManyToOne
    @JoinColumn(name = "shopping_cart_id", nullable = false)
    private ShoppingCart ShoppingCart;

    @Column(name = "shipping_status", nullable = false)
    private String status;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "date_created")
    private LocalDate dateCreated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public com.ecommerce.model.ShoppingCart getShoppingCart() {
        return ShoppingCart;
    }

    public void setShoppingCart(com.ecommerce.model.ShoppingCart shoppingCart) {
        ShoppingCart = shoppingCart;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }
}
