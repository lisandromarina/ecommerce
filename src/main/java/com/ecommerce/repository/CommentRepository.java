package com.ecommerce.repository;

import com.ecommerce.DTO.CommentDTO;
import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(
            "SELECT new com.ecommerce.DTO.CommentDTO(" +
                    "c.id, " +
                    "c.description, " +
                    "u.id, " +
                    "p.id) " +
                    "FROM Comment c " +
                    "INNER JOIN User u ON (u.id = c.user.id) " +
                    "INNER JOIN Product p ON (p.id = c.product.id) " +
                    "WHERE p.id = :productId"
    )
    List<CommentDTO> findCommentsByIProductId(@Param("productId") Long productId);

    @Query(
            "SELECT new com.ecommerce.DTO.CommentDTO(" +
                    "c.id, " +
                    "c.description, " +
                    "u.id, " +
                    "u.firstName, " +
                    "u.lastName, " +
                    "u.email, " +
                    "p.id, " +
                    "p.name, " +
                    "p.price, " +
                    "p.description) " +
                    "FROM Comment c " +
                    "INNER JOIN User u ON (u.id = c.user.id) " +
                    "INNER JOIN Product p ON (p.id = c.product.id) " +
                    "WHERE c.id = :commentId"
    )
    CommentDTO findCommentDTOById(@Param("commentId") Long commentId);
}
