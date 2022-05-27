package com.ecommerce.repository;

import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
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

    @Query(
            "SELECT new com.ecommerce.DTO.ProductDTO(" +
                    "p.id, " +
                    "p.name, " +
                    "p.price) " +
                    "FROM Product p "
    )
    List<ProductDTO> findAllDTO();

    @Query(
            "SELECT new com.ecommerce.DTO.ProductDTO(" +
                    "p.id, " +
                    "p.name, " +
                    "p.price) " +
                    "FROM Product p " +
                    "WHERE p.id = :productId"
    )
    ProductDTO findProductDTOById(@Param("productId") Long productId);
}
