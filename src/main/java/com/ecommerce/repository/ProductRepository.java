package com.ecommerce.repository;

import com.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying
    void deleteProductById(Long id);

    Optional<Product> findProductById(Long id);
}
