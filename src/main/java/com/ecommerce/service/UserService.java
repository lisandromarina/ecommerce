package com.ecommerce.service;

import com.ecommerce.DTO.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserService {

    public ResponseEntity<?> saveUser(UserDTO userDTO, String siteUrl);

    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO);

    public void update(UserDTO userDTO);

    public List<UserDTO> findAll();

    public UserDTO findById(Long userId);

    public void delete(Long userId);
}
