package com.ecommerce.repository;

import com.ecommerce.DTO.UserDTO;
import com.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(
            "SELECT new com.ecommerce.DTO.UserDTO(" +
                    "u.id, " +
                    "u.firstName, " +
                    "u.lastName, " +
                    "u.dateCreated, " +
                    "u.email, " +
                    "u.role) " +
                    "FROM User u "
    )
    List<UserDTO> findAllUsers();

    @Query(
            "SELECT new com.ecommerce.DTO.UserDTO(" +
                    "u.id, " +
                    "u.firstName, " +
                    "u.lastName, " +
                    "u.dateCreated, " +
                    "u.email, " +
                    "u.role) " +
                    "FROM User u " +
                    "WHERE u.id = :userId"
    )
    UserDTO findUserDTOById(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User u " +
            "SET u.isActive = false " +
            "WHERE u.id = :userId")
    void invalidateProductById(@Param("userId") Long userId);
}