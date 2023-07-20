package com.ecommerce.services;

import com.ecommerce.DTO.CommentDTO;
import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.DTO.UserDTO;
import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.model.Comment;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.CommentRepository;
import com.ecommerce.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    private CommentServiceImpl commentService;
    @Mock
    CommentRepository commentRepository;

    @Test
    void testSaveComment() {
        // Define test data
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setDescription("Test comment");

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        commentDTO.setProduct(productDTO);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(2L);
        commentDTO.setUser(userDTO);

        // Mock the behavior of the CommentRepository
        Comment savedComment = new Comment();
        savedComment.setId(123L);
        savedComment.setDescription(commentDTO.getDescription());
        Product product = new Product();
        productDTO.setId(1L);
        savedComment.setProduct(product);
        savedComment.setUser(new User(commentDTO.getUser().getId()));

        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);

        // Call the save method
        Comment savedCommentResult = commentService.save(commentDTO);

        // Verify that the CommentRepository.save method was called with the correct Comment object
        verify(commentRepository, times(1)).save(any(Comment.class));

        // Assertions
        assertEquals(savedComment.getId(), savedCommentResult.getId());
        assertEquals(savedComment.getDescription(), savedCommentResult.getDescription());
        // Add more assertions for other fields if necessary
    }

    @Test
    void testFindCommentById() {
        // Define test data
        Long commentId = 123L;
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentId);
        // Mock the behavior of the CommentRepository
        when(commentRepository.findCommentDTOById(commentId)).thenReturn(commentDTO);

        // Call the findById method
        CommentDTO foundComment = commentService.findById(commentId);

        // Verify that the CommentRepository.findCommentDTOById method was called with the correct ID
        verify(commentRepository, times(1)).findCommentDTOById(commentId);

        // Assertions
        assertNotNull(foundComment);
        assertEquals(commentId, foundComment.getId());
        // Add more assertions for other fields if necessary
    }

    @Test
    void testFindCommentsByProductId() {
        // Define test data
        Long productId = 456L;
        List<CommentDTO> commentDTOList = new ArrayList<>();
        // Add test CommentDTO objects to the list

        // Mock the behavior of the CommentRepository
        when(commentRepository.findCommentsByIProductId(productId)).thenReturn(commentDTOList);

        // Call the findCommentsByProductId method
        List<CommentDTO> foundComments = commentService.findCommentsByIProductId(productId);

        // Verify that the CommentRepository.findCommentsByIProductId method was called with the correct productId
        verify(commentRepository, times(1)).findCommentsByIProductId(productId);

        // Assertions
        assertNotNull(foundComments);
        assertEquals(commentDTOList.size(), foundComments.size());
        // Add more assertions for the content of the lists if necessary
    }

    @Test
    void testSaveComment_ApiRequestException() {
        // Define test data
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setDescription("Test comment");

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        commentDTO.setProduct(productDTO);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(2L);
        commentDTO.setUser(userDTO);

        // Mock the behavior of the CommentRepository to throw an exception when save() is called
        when(commentRepository.save(any(Comment.class))).thenThrow(new RuntimeException("Error saving comment"));

        // Call the save method and expect an ApiRequestException to be thrown
        assertThrows(ApiRequestException.class, () -> commentService.save(commentDTO));
    }
}
