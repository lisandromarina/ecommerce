package com.ecommerce.controller;

import com.ecommerce.DTO.UserDTO;
import com.ecommerce.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @PostMapping("/create")
    public void createUser(@RequestBody UserDTO userDTO) {
        userServiceImpl.save(userDTO);
    }

    @PutMapping("/update")
    public void updateUser(UserDTO userDTO) {
        //userServiceImpl.create(userDTO);
    }

    @GetMapping("/findAll")
    public List<UserDTO> findAllUser() {
        return userServiceImpl.findAll();
    }

    @GetMapping("/findById/{userId}")
    public UserDTO findUserById(@PathVariable("userId") Long userId) {
        return userServiceImpl.findById(userId);
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userServiceImpl.delete(userId);
    }

    @GetMapping("/login/{userEmail}")
    public UserDTO login(@PathVariable("userEmail") String userEmail) {
        return userServiceImpl.login(userEmail);
    }
}
