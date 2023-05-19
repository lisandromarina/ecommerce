package com.ecommerce.DTO;

import com.ecommerce.model.User;

import javax.persistence.Column;
import javax.persistence.JoinColumn;

public class AddressDTO {
    private Long id;
    private String postalCode;
    private String street;
    private String streetNumber;
    private String location;
    private String country;
    private User user;
    private Boolean isActive;
    private Boolean isDeleted;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public AddressDTO(Long id, String street, String streetNumber, String postalCode,
                      String location, String country, Boolean active, Boolean isDeleted) {
        this.id = id;
        this.postalCode = postalCode;
        this.street = street;
        this.streetNumber = streetNumber;
        this.location = location;
        this.country = country;
        this.isActive = active;
        this.isDeleted = isDeleted;
    }
}
