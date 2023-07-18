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
                    "u.id," +
                    "u.username, " +
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
                    "u.id," +
                    "u.username, " +
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
    void invalidateUserById(@Param("userId") Long userId);

    @Query(
            "SELECT new com.ecommerce.DTO.UserDTO(" +
                    "u.id," +
                    "u.username, " +
                    "u.firstName, " +
                    "u.lastName, " +
                    "u.dateCreated, " +
                    "u.email, " +
                    "u.role, " +
                    "u.password) " +
                    "FROM User u " +
                    "WHERE u.username = :username " +
                    "AND u.isActive = true"
    )
    UserDTO findUserByUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM User u WHERE u.username = :username")
    boolean existsByUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM User u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.verificationCode = :code")
    public User findByVerificationCode(@Param("code") String code);
}
