package com.ecommerce.controller;

import com.ecommerce.DTO.UserDTO;
import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.JwtUserDetailsService;
import com.ecommerce.utils.JwtTokenUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final UserRepository userRepository;
    private final JwtUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(UserRepository userRepository,JwtUserDetailsService userDetailsService,
                                    JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) throws Exception {
        Map<String, Object> responseMap = new HashMap<>();
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);

            System.out.println(authenticationToken.getPrincipal().toString());
            System.out.println(authenticationToken.getCredentials().toString());
            System.out.println(authenticationToken);

            Authentication auth = authenticationManager.authenticate(authenticationToken);
            System.out.println("paso el auth");
            if (auth.isAuthenticated()) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                System.out.println(username);
                String token = jwtTokenUtil.generateToken(userDetails);
                System.out.println(" SOY EL TOKEN");
                responseMap.put("error", false);
                responseMap.put("message", "Logged In");
                responseMap.put("token", token);
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("error", true);
                responseMap.put("message", "Invalid Credentials");
                return ResponseEntity.status(401).body(responseMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("error", true);
            responseMap.put("message", "Something went wrong");
            return ResponseEntity.status(500).body(responseMap);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) {

        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setDateCreated(LocalDate.now());
        user.setActive(true);
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        user.setRole(Role.USER);

        Map<String, Object> responseMap = new HashMap<>();
        UserDetails userDetails = userDetailsService.createUserDetails(user.getUsername(), user.getPassword());
        String token = jwtTokenUtil.generateToken(userDetails);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }

        responseMap.put("username", user.getUsername());
        responseMap.put("message", "Account created successfully");
        responseMap.put("token", token);
        return ResponseEntity.ok(responseMap);
    }
}