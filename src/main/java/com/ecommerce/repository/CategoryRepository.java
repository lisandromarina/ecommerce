package com.ecommerce.repository;

import com.ecommerce.DTO.CategoryDTO;
import com.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(
            "SELECT new com.ecommerce.DTO.CategoryDTO(" +
                    "c.id, " +
                    "c.name, " +
                    "c.isActive) " +
                    "FROM Category c " +
                    "WHERE c.isActive = true"
    )
    List<CategoryDTO> findAllDTO();

    @Query(
            "SELECT new com.ecommerce.DTO.CategoryDTO(" +
                    "c.id, " +
                    "c.name, " +
                    "c.isActive) " +
                    "FROM Category c " +
                    "WHERE c.id = :categoryId"
    )
    CategoryDTO findCategoryById(@Param("categoryId") Long categoryId);

    @Modifying
    @Query("UPDATE Category c " +
            "SET c.isActive = false " +
            "WHERE c.id = :categoryId")
    void invalidateCategoryById(@Param("categoryId") Long categoryId);
}
