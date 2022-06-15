package com.ecommerce.service.impl;

import com.ecommerce.DTO.UserDTO;
import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.AbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements AbmService<UserDTO> {

    @Autowired
    UserRepository userRepository;

    public void save(UserDTO userDTO) {
        validateUserFields(userDTO);

        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setDateCreated(LocalDate.now());
        user.setRole(userDTO.getRole());
        user.setActive(true);

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
            userRepository.invalidateUserById(userId);
        }catch (Exception e){
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    public String login(String email){
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        String username = authentication.getName();
        return username;
    }

    private void validateUserFields(UserDTO userDTO) {
        if (userDTO.getFirstName() == null
                || userDTO.getFirstName().isEmpty()
                || userDTO.getLastName() == null
                || userDTO.getLastName().isEmpty()
                || userDTO.getEmail() == null
                || userDTO.getEmail().isEmpty()
                || userDTO.getRole() == null){
            throw new ApiRequestException("The User cannot have empty fields", HttpStatus.BAD_REQUEST);
        }
    }



    private void validateUserExist(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ApiRequestException("The User with id: " + id + " doesn't exist",
                    HttpStatus.NOT_FOUND);
        }
    }
}
