package com.ecommerce.services;

import com.ecommerce.DTO.UserDTO;
import com.ecommerce.model.Role;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.impl.JwtUserDetailsService;
import com.ecommerce.utils.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtUserDetailsServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    JwtUserDetailsService jwtUserDetailsService;

    @Test
    void testLoadUserByUsername() {
        // Define test data
        String username = "testUser";
        String password = "testPassword";

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        userDTO.setRole(Role.USER);

        // Mock the behavior of userRepository.findUserByUsername()
        when(userRepository.findUserByUsername(username)).thenReturn(userDTO);

        // Call the loadUserByUsername method
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

        // Assertions
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(Role.USER.name())));
    }

    @Test
    void testValidateToken_ValidToken() {
        // Define test data
        String token = "validToken";
        String username = "testUser";
        String password = "testPassword";

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        userDTO.setRole(Role.USER);

        // Mock the behavior of jwtTokenUtil.getUsernameFromToken()
        when(jwtTokenUtil.getUsernameFromToken(token)).thenReturn(username);

        // Mock the behavior of userRepository.findUserByUsername()
        when(userRepository.findUserByUsername(username)).thenReturn(userDTO);

        // Mock the behavior of jwtTokenUtil.validateToken()
        when(jwtTokenUtil.validateToken(token, new User(username, password, Collections.emptyList()))).thenReturn(true);

        // Call the validateToken method
        Boolean result = jwtUserDetailsService.validateToken(token);

        // Assertions
        assertTrue(result);
    }

    @Test
    void testValidateToken_InvalidToken() {
        // Define test data
        String token = "invalidToken";

        // Mock the behavior of jwtTokenUtil.getUsernameFromToken()
        when(jwtTokenUtil.getUsernameFromToken(token)).thenReturn(null);

        // Call the validateToken method
        Boolean result = jwtUserDetailsService.validateToken(token);

        // Assertions
        assertFalse(result);
    }
}
