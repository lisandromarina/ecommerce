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
                    "sc.status) " +
                    "FROM ShoppingCart sc " +
                    "WHERE sc.id = :id"
    )
    ShoppingCartDTO findShoppingCartDTOById(@Param("id") Long id);
}
