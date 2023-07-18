package com.ecommerce.service.impl;

import com.ecommerce.DTO.CommentDTO;
import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.model.Comment;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.CommentRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Override

    public Comment save(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setDescription(commentDTO.getDescription());

        Product product = new Product();
        product.setId(commentDTO.getProduct().getId());
        comment.setProduct(product);

        User user = new User();
        user.setId(commentDTO.getUser().getId());
        comment.setUser(user);

        try {
            return commentRepository.save(comment);
        }catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    @Override
    public void update(CommentDTO objectDTO) {
        //TODO
    }

    @Override
    public CommentDTO findById(Long id) {
        return commentRepository.findCommentDTOById(id);
    }

    @Override
    public List<CommentDTO> findCommentsByIProductId(Long productId) {
        return commentRepository.findCommentsByIProductId(productId);
    }

    @Override
    public void delete(Long id) {

    }
}
