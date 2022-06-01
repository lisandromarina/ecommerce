package com.ecommerce.service.impl;

import com.ecommerce.DTO.UserDTO;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.AbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements AbmService<UserDTO> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public void save(UserDTO userDTO) {
        User user = new User();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setDateCreated(LocalDate.now());
        user.setEmail(userDTO.getEmail());

        Role role = roleRepository.findById(userDTO.getRoleId()).orElseThrow();
        user.setRole(role);

        userRepository.save(user);
    }

    @Override
    public void update(UserDTO userDTO) {
        //userService.createUser(userDTO);
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAllUsers();
    }

    @Override
    public UserDTO findById(Long userId) {
        return userRepository.findUserDTOById(userId);
    }

    @Override
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }
}
