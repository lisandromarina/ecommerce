package com.ecommerce.services;

import com.ecommerce.DTO.UserDTO;
import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.EmailService;
import com.ecommerce.service.impl.EmailServiceImpl;
import com.ecommerce.service.impl.UserServiceImpl;
import com.ecommerce.utils.JwtTokenUtil;
import com.ecommerce.utils.email.VerificationAccountEmailMessage;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailServiceImpl emailService;
    @Mock
    private JwtTokenUtil jwtTokenUtil;
    @Mock
    AuthenticationManager authenticationManager;
    @Test
    public void saveUserSuccess() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("oneName");
        userDTO.setLastName("oneLastName");
        userDTO.setEmail("oneEmial");
        userDTO.setUsername("oneUsername");
        userDTO.setPassword(new BCryptPasswordEncoder().encode("onePassword"));

        User user = new User();
        user.setId(1l);
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<?> response = userService.saveUser(userDTO, "example.com");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, userDTO.getId());
    }
    @Test
    public void saveUserFailureNotFirstName() {
        UserDTO userDTO = new UserDTO();
        userDTO.setLastName("oneLastName");
        userDTO.setEmail("oneEmial");
        userDTO.setUsername("oneUsername");
        userDTO.setPassword(new BCryptPasswordEncoder().encode("onePassword"));

        ApiRequestException thrown = assertThrows(ApiRequestException.class,() -> userService.saveUser(userDTO, "example.com"),
                "The User cannot have empty fields");

        assertTrue(thrown.getMessage().contains("The User cannot have empty fields"));
    }

    @Test
    public void saveUserFailureNotLastName() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("oneFristName");
        userDTO.setEmail("oneEmial");
        userDTO.setUsername("oneUsername");
        userDTO.setPassword(new BCryptPasswordEncoder().encode("onePassword"));

        ApiRequestException thrown = assertThrows(ApiRequestException.class,() -> userService.saveUser(userDTO, "example.com"),
                "The User cannot have empty fields");

        assertTrue(thrown.getMessage().contains("The User cannot have empty fields"));
    }

    @Test
    public void saveUserFailureNotEmail() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("oneFristName");
        userDTO.setLastName("oneLastName");
        userDTO.setUsername("oneUsername");
        userDTO.setPassword(new BCryptPasswordEncoder().encode("onePassword"));

        ApiRequestException thrown = assertThrows(ApiRequestException.class,() -> userService.saveUser(userDTO, "example.com"),
                "The User cannot have empty fields");

        assertTrue(thrown.getMessage().contains("The User cannot have empty fields"));
    }

    @Test
    public void saveUserFailureNotPassword() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("oneFristName");
        userDTO.setLastName("oneLastName");
        userDTO.setEmail("oneEmail");

        ApiRequestException thrown = assertThrows(ApiRequestException.class,() -> userService.saveUser(userDTO, "example.com"),
                "The User cannot have empty fields");

        assertTrue(thrown.getMessage().contains("The User cannot have empty fields"));
    }

    @Test
    public void saveUserFailureUsernameAlreadyExists() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("oneName");
        userDTO.setLastName("oneLastName");
        userDTO.setEmail("oneEmial");
        userDTO.setUsername("oneUsername");
        userDTO.setPassword(new BCryptPasswordEncoder().encode("onePassword"));

        when(userRepository.existsByUsername(userDTO.getUsername())).thenReturn(true);

        ApiRequestException thrown = assertThrows(ApiRequestException.class,() -> userService.saveUser(userDTO, "example.com"),
                "Username arleady exist!");

        assertTrue(thrown.getHttpStatus().equals(HttpStatus.BAD_REQUEST));
        assertTrue(thrown.getMessage().contains("Username arleady exist!"));
    }

    @Test
    public void saveUserFailureEmailAlreadyExists() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("oneName");
        userDTO.setLastName("oneLastName");
        userDTO.setEmail("oneEmial");
        userDTO.setUsername("oneUsername");
        userDTO.setPassword(new BCryptPasswordEncoder().encode("onePassword"));

        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(true);

        ApiRequestException thrown = assertThrows(ApiRequestException.class,() -> userService.saveUser(userDTO, "example.com"),
                "Email arleady exist!");

        assertTrue(thrown.getHttpStatus().equals(HttpStatus.BAD_REQUEST));
        assertTrue(thrown.getMessage().contains("Email arleady exist!"));
    }

    @Test
    void testVerifyReturnsTrue() {
        String verificationCode = "your_verification_code";
        User user = new User();
        user.setVerificationCode(verificationCode);
        user.setActive(false);

        // Mock userRepository.findByVerificationCode() to return the User object
        when(userRepository.findByVerificationCode(verificationCode)).thenReturn(user);

        // Call the verify method
        boolean result = userService.verify(verificationCode);

        // Assert that the result is true, indicating successful verification
        assertTrue(result);
    }

    @Test
    void testVerifyReturnsFalseWhenUserIsNull() {
        String verificationCode = "your_verification_code";

        // Mock userRepository.findByVerificationCode() to return null
        when(userRepository.findByVerificationCode(verificationCode)).thenReturn(null);

        // Call the verify method
        boolean result = userService.verify(verificationCode);

        // Assert that the result is false, indicating unsuccessful verification
        assertFalse(result);
    }

    @Test
    void testVerifyReturnsFalseWhenUserIsActive() {
        String verificationCode = "your_verification_code";
        User user = new User();
        user.setVerificationCode(verificationCode);
        user.setActive(true);

        // Mock userRepository.findByVerificationCode() to return the User object with active set to true
        when(userRepository.findByVerificationCode(verificationCode)).thenReturn(user);

        // Call the verify method
        boolean result = userService.verify(verificationCode);

        // Assert that the result is false, indicating unsuccessful verification
        assertFalse(result);
    }

    @Test
    void testVerifyThrowsExceptionWhenRepositoryFails() {
        String verificationCode = "your_verification_code";

        // Mock userRepository.findByVerificationCode() to throw an exception
        when(userRepository.findByVerificationCode(verificationCode)).thenThrow(new RuntimeException("Some error message"));

        // Assert that the verify method throws an ApiRequestException with the expected error message
        ApiRequestException thrown = assertThrows(ApiRequestException.class,
                () -> userService.verify(verificationCode),
                "verify should throw an ApiRequestException when userRepository.findByVerificationCode() fails");

        assertTrue(thrown.getMessage().contains("Some error message"));
    }

    @Test
    void testFindAllReturnsListOfUserDTOs() {
        List<UserDTO> users = new ArrayList<>();
        UserDTO user1 = new UserDTO();
        user1.setId(1L);
        user1.setUsername("user1");
        UserDTO user2 = new UserDTO();
        user2.setId(2L);
        user2.setUsername("user2");
        users.add(user1);
        users.add(user2);

        when(userRepository.findAllUsers()).thenReturn(users);

        List<UserDTO> result = userService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(1L, result.get(0).getId());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals(2L, result.get(1).getId());
        assertEquals("user2", result.get(1).getUsername());
    }

    @Test
    void testFindAllThrowsExceptionWhenRepositoryFails() {
        when(userRepository.findAllUsers()).thenThrow(new RuntimeException("Some error message"));

        ApiRequestException thrown = assertThrows(ApiRequestException.class,
                () -> userService.findAll(),
                "findAll should throw an ApiRequestException when userRepository.findAllUsers() fails");

        assertTrue(thrown.getMessage().contains("Some error message"));
    }

    @Test
    void testFindByIdReturnsUserDTO() {
        long userId = 1L;
        UserDTO user = new UserDTO();
        user.setId(userId);
        user.setUsername("user1");

        when(userRepository.findUserDTOById(userId)).thenReturn(user);
        when(userRepository.existsById(userId)).thenReturn(true);

        UserDTO result = userService.findById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("user1", result.getUsername());
    }

    @Test
    void testFindByIdThrowsApiRequestExceptionWhenUserNotFound() {
        // Set a user ID that does not exist
        long userId = 123L;

        // Call the findById method and expect an ApiRequestException to be thrown
        assertThrows(ApiRequestException.class, () -> userService.findById(userId));
    }

    @Test
    void testValidateUserExistUserDoesNotExist() {
        long userId = 1L;

        // Mock userRepository.existsById() to return false, indicating user does not exist
        when(userRepository.existsById(userId)).thenReturn(false);

        // Call the validateUserExist method and expect an ApiRequestException to be thrown
        ApiRequestException thrown = assertThrows(ApiRequestException.class, () -> userService.findById(userId));

        // Verify that userRepository.existsById() is called with the correct user ID
        verify(userRepository).existsById(userId);

        // Verify the exception message and status code
        String expectedMessage = "The User with id: " + userId + " doesn't exist";
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        assertEquals(expectedMessage, thrown.getMessage());
        assertEquals(expectedStatus, thrown.getHttpStatus());
    }

    @Test
    void testLoginUserSuccessful() {
        String username = "testUser";
        String password = "testPassword";
        String token = "testToken";

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("message", "Logged In");
        responseMap.put("token", token);

        // Mock userRepository.findUserByUsername() to return the user
        when(userRepository.findUserByUsername(username)).thenReturn(userDTO);

        // Mock authenticationManager.authenticate() to return an authenticated Authentication object
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        // Mock jwtTokenUtil.generateToken() to return the token
        when(jwtTokenUtil.generateToken(userDTO)).thenReturn(token);

        // Call the loginUser method
        ResponseEntity<?> response = userService.loginUser(userDTO);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseMap, response.getBody());

        // Verify that userRepository.findUserByUsername() is called with the correct username
        verify(userRepository).findUserByUsername(username);

        // Verify that authenticationManager.authenticate() is called with the correct authentication token
        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(username, password));

        // Verify that jwtTokenUtil.generateToken() is called with the correct userDTO
        verify(jwtTokenUtil).generateToken(userDTO);
    }

    @Test
    void testLoginUserInvalidCredentials() {
        String username = "testUser";
        String password = "testPassword";

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);

        // Mock authenticationManager.authenticate() to return an unauthenticated Authentication object
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        // Call the loginUser method and expect an ApiRequestException to be thrown
        ApiRequestException thrown = assertThrows(ApiRequestException.class, () -> userService.loginUser(userDTO),
                "Invalid credentials");

        // Verify that userRepository.findUserByUsername() is called with the correct username
        verifyNoInteractions(userRepository);

        // Verify that authenticationManager.authenticate() is called with the correct authentication token
        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(username, password));

        // Verify that jwtTokenUtil.generateToken() is not called since the authentication failed
        verifyNoInteractions(jwtTokenUtil);

        // Verify the exception message and status code
        String expectedMessage = "Invalid credentials";
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    void testDelete_Successful() {
        Long userId = 1L;
        String authenticatedUsername = "user1";
        Principal principal = mock(Principal.class);

        // Mock the userRepository.findUserDTOById() method to return a valid UserDTO
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setUsername(authenticatedUsername);
        when(userRepository.findUserDTOById(userId)).thenReturn(userDTO);
        when(userRepository.existsById(userId)).thenReturn(true);
        // Mock the principal.getName() method to return the authenticated user's username
        when(principal.getName()).thenReturn(authenticatedUsername);

        // Mock the userRepository.invalidateUserById() method to not throw any exception
        doNothing().when(userRepository).invalidateUserById(userId);

        // Call the delete method
        userService.delete(userId, principal);

        // Verify that userRepository.findUserDTOById() and userRepository.invalidateUserById() were called
        verify(userRepository, times(1)).findUserDTOById(userId);
        verify(userRepository, times(1)).invalidateUserById(userId);
    }

    @Test
    void testDelete_UnauthorizedUser() {
        Principal principal = mock(Principal.class);
        Long userId = 1L;
        String authenticatedUsername = "user1";
        String targetUsername = "user2";

        // Mock the userRepository.findUserDTOById() method to return a valid UserDTO with a different username
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setUsername(targetUsername);
        when(userRepository.findUserDTOById(userId)).thenReturn(userDTO);
        when(userRepository.existsById(userId)).thenReturn(true);

        // Mock the principal.getName() method to return the authenticated user's username
        when(principal.getName()).thenReturn(authenticatedUsername);

        // Call the delete method and expect an ApiRequestException to be thrown
        assertThrows(ApiRequestException.class, () -> userService.delete(userId, principal));

        // Verify that userRepository.findUserDTOById() was called
        verify(userRepository, times(1)).findUserDTOById(userId);

        // Verify that userRepository.invalidateUserById() was not called
        verify(userRepository, never()).invalidateUserById(userId);
    }
}
