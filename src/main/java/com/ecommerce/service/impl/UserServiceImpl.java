package com.ecommerce.service.impl;

import com.ecommerce.DTO.UserDTO;
import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.UserService;
import com.ecommerce.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUserDetailsService userDetailsService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<?> saveUser(UserDTO userDTO) {
        validateUserFields(userDTO);

        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setDateCreated(LocalDate.now());
        user.setActive(true);
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        user.setRole(Role.USER);

        try {
            Long userId = userRepository.save(user).getId();
            userDTO.setId(userId);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }

        String token = jwtTokenUtil.generateToken(userDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("username", user.getUsername());
        responseMap.put("message", "Account created successfully");
        responseMap.put("token", token);
        return ResponseEntity.ok(responseMap);
    }

    @Override
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        Map<String, Object> responseMap = new HashMap<>();
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);

            Authentication auth = authenticationManager.authenticate(authenticationToken);
            if (auth.isAuthenticated()) {
                UserDTO user = userRepository.findUserByUsername(username);
                String token = jwtTokenUtil.generateToken(user);
                responseMap.put("error", false);
                responseMap.put("message", "Logged In");
                responseMap.put("token", token);
                return ResponseEntity.ok(responseMap);
            }else{
                throw new ApiRequestException("Invalid credentials", HttpStatus.BAD_REQUEST);
            }
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

    private void validateUserFields(UserDTO userDTO) {
        if (userDTO.getFirstName() == null
                || userDTO.getFirstName().isEmpty()
                || userDTO.getLastName() == null
                || userDTO.getLastName().isEmpty()
                || userDTO.getEmail() == null
                || userDTO.getEmail().isEmpty()
                || userDTO.getPassword() == null
                || userDTO.getPassword().isEmpty()){
            throw new ApiRequestException("The User cannot have empty fields", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByUsername(userDTO.getUsername())){
            throw new ApiRequestException("Username arleady exist!", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new ApiRequestException("Email arleady exist!", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateUserExist(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ApiRequestException("The User with id: " + id + " doesn't exist",
                    HttpStatus.NOT_FOUND);
        }
    }
}
