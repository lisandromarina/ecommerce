package com.ecommerce.DTO;

import com.ecommerce.model.User;

import javax.persistence.Column;
import javax.persistence.JoinColumn;

public class AddressDTO {
    private Long id;
    private String fullName;
    private String postalCode;
    private String street;
    private String streetNumber;
    private String location;
    private String province;
    private String country;
    private String department;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public AddressDTO() {
    }

    public AddressDTO(Long id, String fullName, String street, String streetNumber, String department, String postalCode,
                      String location,String province, String country, Boolean active, Boolean isDeleted) {
        this.id = id;
        this.fullName = fullName;
        this.postalCode = postalCode;
        this.street = street;
        this.streetNumber = streetNumber;
        this.department = department;
        this.location = location;
        this.province = province;
        this.country = country;
        this.isActive = active;
        this.isDeleted = isDeleted;
    }
}
