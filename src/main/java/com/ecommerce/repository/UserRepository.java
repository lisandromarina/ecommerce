package com.ecommerce.repository;

import com.ecommerce.DTO.UserDTO;
import com.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    void deleteUserById(Long id);

    @Query(
            "SELECT new com.ecommerce.DTO.UserDTO(" +
                    "u.id, " +
                    "u.firstName, " +
                    "u.lastName, " +
                    "u.dateCreated, " +
                    "u.email, " +
                    "r.id, " +
                    "r.name) " +
                    "FROM User u " +
                    "INNER JOIN Role r ON (u.role.id = r.id)"
    )
    List<UserDTO> findAllUsers();

    @Query(
            "SELECT new com.ecommerce.DTO.UserDTO(" +
                    "u.id, " +
                    "u.firstName, " +
                    "u.lastName, " +
                    "u.dateCreated, " +
                    "u.email, " +
                    "r.id, " +
                    "r.name) " +
                    "FROM User u " +
                    "INNER JOIN Role r ON (u.role.id = r.id) " +
                    "WHERE u.id = :userId"
    )
    UserDTO findUserDTOById(@Param("userId") Long userId);
}
