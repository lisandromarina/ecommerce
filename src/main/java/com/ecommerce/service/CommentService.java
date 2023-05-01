package com.ecommerce.service;

import com.ecommerce.DTO.CommentDTO;
import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.model.Comment;
import com.ecommerce.model.Product;

import java.util.List;

public interface CommentService {

    Comment save(CommentDTO objectDTO);

    void update(CommentDTO objectDTO);

    CommentDTO findById(Long id);

    List<CommentDTO> findCommentsByIProductId(Long productId);
    void delete(Long id);

}
