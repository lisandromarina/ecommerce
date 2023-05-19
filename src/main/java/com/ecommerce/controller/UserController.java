package com.ecommerce.controller;

import com.ecommerce.DTO.UserDTO;
import com.ecommerce.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @PutMapping("/update")
    public void updateUser(UserDTO userDTO) {
        //userServiceImpl.create(userDTO);
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userServiceImpl.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
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

}
