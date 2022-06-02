package com.ecommerce.service.impl;

import com.ecommerce.DTO.UserDTO;
import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.AbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements AbmService<UserDTO> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public void save(UserDTO userDTO) {
        validateUserFields(userDTO);

        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setDateCreated(LocalDate.now());
        user.setActive(true);

        Role role = getRoleById(userDTO.getRoleId());
        user.setRole(role);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    @Override
    public void update(UserDTO userDTO) {
        //userService.createUser(userDTO);
    }

    @Override
    public List<UserDTO> findAll() {
        try {
            return userRepository.findAllUsers();
        }catch (Exception e){
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    @Override
    public UserDTO findById(Long userId) {
        validateUserExist(userId);
        try{
            return userRepository.findUserDTOById(userId);
        }catch (Exception e){
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long userId) {
        validateUserExist(userId);
        try{
            userRepository.invalidateProductById(userId);
        }catch (Exception e){
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    private void validateUserFields(UserDTO userDTO) {
        if (userDTO.getFirstName() == null
                || userDTO.getFirstName().isEmpty()
                || userDTO.getLastName() == null
                || userDTO.getLastName().isEmpty()
                || userDTO.getEmail() == null
                || userDTO.getEmail().isEmpty()
                || userDTO.getRoleId() == null){
            throw new ApiRequestException("The User cannot have empty fields", HttpStatus.BAD_REQUEST);
        }
    }

    private Role getRoleById(Long idRole) {
        Optional<Role> role;
        try {
            role = roleRepository.findById(idRole);
            if (!role.isPresent()) {
                throw new ApiRequestException("Cannot create user, role with id: " + idRole + " not found", HttpStatus.NOT_FOUND);
            }
            return role.get();
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    private void validateUserExist(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ApiRequestException("The User with id: " + id + " doesn't exist",
                    HttpStatus.NOT_FOUND);
        }
    }
}
