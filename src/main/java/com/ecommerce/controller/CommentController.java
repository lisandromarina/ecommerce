package com.ecommerce.controller;

import com.ecommerce.DTO.CommentDTO;
import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.service.CommentService;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@CrossOrigin(origins = "https://ecommerce-pdq0.onrender.com", allowCredentials = "true")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/findById/{id}")
    public CommentDTO findCommentById(@PathVariable("id") Long id) {
        return commentService.findById(id);
    }

    @GetMapping("/findProductByProductId/{productId}")
    public List<CommentDTO> findCommentByProductId(@PathVariable("productId") Long productId){
        return commentService.findCommentsByIProductId(productId);
    }

    @PostMapping(path = "/save")
    public void saveComment(@RequestBody CommentDTO commentDTO) {
        commentService.save(commentDTO);
    }

    @PutMapping("/update")
    public void updateComment(@RequestBody CommentDTO commentDTO) {
        commentService.update(commentDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCommentById(@PathVariable("id") Long id) {
        commentService.delete(id);
    }
}
