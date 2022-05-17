package com.ecommerce.repository;

import com.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying
    void deleteProductById(Long id);

    Optional<Product> findProductById(Long id);

    @Query(
            "SELECT p.price " +
                    "FROM Product p " +
                    "WHERE p.id = :productId"
    )
    Double findPriceById(@Param("productId") Long productId);
}
