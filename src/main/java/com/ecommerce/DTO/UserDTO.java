package com.ecommerce.DTO;

import com.ecommerce.model.Role;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateCreated;
    private String email;
    private Role role;

    public UserDTO() {
    }

    public UserDTO(Long id, String firstName, String lastName, LocalDate dateCreated,
                   String email, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateCreated = dateCreated;
        this.email = email;
        this.role = role;
    }

    public UserDTO(Long id, String firstName, String lastName, LocalDate dateCreated,
                   String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateCreated = dateCreated;
        this.email = email;
    }

    public UserDTO(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role roleName) {
        this.role = roleName;
    }

}
