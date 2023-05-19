package com.ecommerce.repository;

import com.ecommerce.DTO.AddressDTO;
import com.ecommerce.DTO.CommentDTO;
import com.ecommerce.model.Address;
import com.ecommerce.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query(
            "SELECT new com.ecommerce.DTO.AddressDTO(" +
                    "a.id, " +
                    "a.street, " +
                    "a.streetNumber, " +
                    "a.postalCode, " +
                    "a.location, " +
                    "a.country, " +
                    "a.isActive, " +
                    "a.isDeleted) " +
                    "FROM Address a " +
                    "INNER JOIN User u ON (u.id = a.user.id) " +
                    "WHERE u.id = :userId " +
                    "AND a.isDeleted != true"
    )
    List<AddressDTO> findAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("Update Address a set a.isActive = false WHERE a.isActive = true And a.user.id = :userId")
    void changeIsActiveToFalse(@Param("userId") Long userId);

    @Modifying
    @Query("Update Address a set a.isActive = true WHERE a.user.id = :userId AND a.id = :addressId")
    void activeSelected(@Param("userId") Long userId, @Param("addressId") Long addressId);

    @Query(
            "SELECT new com.ecommerce.DTO.AddressDTO(" +
                    "a.id, " +
                    "a.street, " +
                    "a.streetNumber, " +
                    "a.postalCode, " +
                    "a.location, " +
                    "a.country, " +
                    "a.isActive, " +
                    "a.isDeleted) " +
                    "FROM Address a " +
                    "WHERE a.id = :addressId"
    )
    AddressDTO findAddressById(@Param("addressId") Long addressId);

    @Modifying
    @Query("Update Address a set a.isDeleted = true WHERE a.id = :addressId")
    void deleteById(@Param("addressId") Long addressId);

}
