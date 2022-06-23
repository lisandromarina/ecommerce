package com.ecommerce.controller;

import com.ecommerce.DTO.UserDTO;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) throws Exception {
        return  userService.loginUser(userDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) {
        return userService.saveUser(userDTO);
    }
}