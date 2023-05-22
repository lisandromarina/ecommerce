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
                    "a.fullName, " +
                    "a.street, " +
                    "a.streetNumber, " +
                    "a.department, " +
                    "a.postalCode, " +
                    "a.location, " +
                    "a.province, " +
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
                    "a.fullName, " +
                    "a.street, " +
                    "a.streetNumber, " +
                    "a.department, " +
                    "a.postalCode, " +
                    "a.location, " +
                    "a.province, " +
                    "a.country, " +
                    "a.isActive, " +
                    "a.isDeleted) " +
                    "FROM Address a " +
                    "WHERE a.id = :addressId"
    )
    AddressDTO findAddressById(@Param("addressId") Long addressId);

    @Modifying
    @Query("Update Address a SET a.isDeleted = true WHERE a.id = :addressId")
    void deleteById(@Param("addressId") Long addressId);
    @Modifying
    @Query("Update Address a " +
            "SET a.fullName = CASE WHEN :fullName IS NULL THEN a.fullName ELSE :fullName END, " +
            "a.postalCode = CASE WHEN :postalCode IS NULL THEN a.postalCode ELSE :postalCode END, " +
            "a.province = CASE WHEN :province IS NULL THEN a.province ELSE :province END, " +
            "a.location = CASE WHEN :location IS NULL THEN a.location ELSE :location END, " +
            "a.street = CASE WHEN :street IS NULL THEN a.street ELSE :street END, " +
            "a.streetNumber = CASE WHEN :streetNumber IS NULL THEN a.streetNumber ELSE :streetNumber END, " +
            "a.department = CASE WHEN :department IS NULL THEN a.department ELSE :department END " +
            "WHERE a.id = :addressId")
    void updateAddress(
            @Param("fullName") String fullName,
            @Param("postalCode") String postalCode,
            @Param("province") String province,
            @Param("location") String location,
            @Param("street") String street,
            @Param("streetNumber") String streetNumber,
            @Param("department") String department,
            @Param("addressId") Long addressId);

}
