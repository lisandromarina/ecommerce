package com.ecommerce.controller;

import com.ecommerce.DTO.UserDTO;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public void createUser(@RequestBody UserDTO userDTO){
        userService.save(userDTO);
    }

    @PutMapping("/update")
    public void updateUser(UserDTO userDTO){
        //userService.create(userDTO);
    }

    @GetMapping("/findAll")
    public List<UserDTO> findAllUser(){
        return userService.findAll();
    }

    @GetMapping("/findById/{userId}")
    public UserDTO findUserById(@PathVariable ("userId") Long userId){
        return userService.findById(userId);
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId){
        userService.delete(userId);
    }
}
