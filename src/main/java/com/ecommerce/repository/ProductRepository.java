package com.ecommerce.repository;

import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(
            "SELECT p.price " +
                    "FROM Product p " +
                    "WHERE p.id = :productId"
    )
    Double findPriceById(@Param("productId") Long productId);

    @Query(
            "SELECT new com.ecommerce.DTO.ProductDTO(" +
                    "p.id, " +
                    "p.name, " +
                    "p.price, " +
                    "p.description, " +
                    "p.isActive, " +
                    "c.id, " +
                    "c.name, " +
                    "c.isActive, " +
                    "p.userId) " +
                    "FROM Product p " +
                    "INNER JOIN Category c ON (c.id = p.category.id)" +
                    "WHERE p.isActive = true"
    )
    List<ProductDTO> findAllDTO();

    @Query(
            "SELECT new com.ecommerce.DTO.ProductDTO(" +
                    "p.id, " +
                    "p.name, " +
                    "p.price," +
                    "p.description,  " +
                    "p.isActive, " +
                    "c.id, " +
                    "c.name, " +
                    "c.isActive, " +
                    "p.userId) " +
                    "FROM Product p " +
                    "INNER JOIN Category c ON (c.id = p.category.id) " +
                    "WHERE p.id = :productId"
    )
    ProductDTO findProductDTOById(@Param("productId") Long productId);

    @Modifying
    @Query("UPDATE Product p " +
            "SET p.isActive = false " +
            "WHERE p.id = :productId")
    void invalidateProductById(@Param("productId") Long productId);

    @Modifying
    @Query("UPDATE Product p " +
            "SET p.category = null " +
            "WHERE p.category.id = :categoryId")
    void changeCategoryInAllProductByCategoryId(@Param("categoryId") Long categoryId);
}
