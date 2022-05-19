package com.ecommerce.service;

import com.ecommerce.DTO.UserDTO;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public void createUser(UserDTO userDTO){
        User user = new User();
        System.out.println(userDTO.getFirstName());
        System.out.println(userDTO.getLastName());
        System.out.println(userDTO.getEmail());
        System.out.println(userDTO.getRoleId());

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setDateCreated(LocalDate.now());
        user.setEmail(userDTO.getEmail());

        Role role = roleRepository.findById(userDTO.getRoleId()).orElseThrow();
        user.setRole(role);

        userRepository.save(user);
    }

    public void updateUser(UserDTO userDTO){
        //userService.createUser(userDTO);
    }

    public List<UserDTO> findAllUser(){
        return userRepository.findAllUsers();
    }

    public UserDTO findUserById(Long userId){
        return userRepository.findUserDTOById(userId);
    }

    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }
}
