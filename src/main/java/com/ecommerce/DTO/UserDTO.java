package com.ecommerce.DTO;

import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

public class UserDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate dateCreated;
    private String email;
    private Role role;
    private String password;

    public UserDTO() {
    }

    public UserDTO(Long id, String username, String firstName, String lastName, LocalDate dateCreated,
                   String email, Role role) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateCreated = dateCreated;
        this.email = email;
        this.role = role;
    }

    public UserDTO(Long id, String username, String firstName, String lastName, LocalDate dateCreated,
                   String email, Role role, String password) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateCreated = dateCreated;
        this.email = email;
        this.role = role;
        this.password = password;
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

    public UserDTO(String username, String password){
        this.username = username;
        this.password = password;
    }

    public UserDTO(Long id){
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
