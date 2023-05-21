package com.ecommerce.repository;

import com.ecommerce.DTO.ShoppingCartDTO;
import com.ecommerce.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    @Query(
            "SELECT new com.ecommerce.DTO.ShoppingCartDTO(" +
                    "sc.id, " +
                    "sc.dateCreated, " +
                    "isComplete) " +
                    "FROM ShoppingCart sc " +
                    "WHERE sc.id = :id"
    )
    ShoppingCartDTO findShoppingCartDTOById(@Param("id") Long id);

    @Query(
            "SELECT new com.ecommerce.DTO.ShoppingCartDTO(" +
                    "sc.id, " +
                    "sc.dateCreated, " +
                    "sc.isComplete) " +
                    "FROM ShoppingCart sc " +
                    "WHERE sc.user.id = :userId AND sc.isComplete = false"
    )
    ShoppingCartDTO findByUserId(@Param("userId") Long userId);

    @Query(
            "SELECT CASE WHEN COUNT(sc) > 0 THEN true ELSE false END " +
                    "FROM ShoppingCart sc WHERE sc.user.id = :userId"
    )
    Boolean existsByUserId(@Param("userId") Long userId);
}
