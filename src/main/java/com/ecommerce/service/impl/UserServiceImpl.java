package com.ecommerce.service.impl;

import com.ecommerce.DTO.UserDTO;
import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.UserService;
import com.ecommerce.utils.JwtTokenUtil;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
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
    private JavaMailSender mailSender;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<?> saveUser(UserDTO userDTO, String siteURL) {
        validateUserFields(userDTO);

        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setDateCreated(LocalDate.now());
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        user.setRole(Role.USER);

        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setActive(false);

        try {
            Long userId = userRepository.save(user).getId();
            userDTO.setId(userId);
            sendVerificationEmail(user, siteURL);
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

    public boolean verify(String verificationCode) {
        try{
        User user = userRepository.findByVerificationCode(verificationCode);
        if (user == null || user.getActive()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setActive(true);
            System.out.println(user.getActive());
            userRepository.save(user);

            return true;
        }
        }catch (Exception e){
            throw new ApiRequestException(e.getMessage(), e);
        }
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

    private void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "lisandromarina1@gmail.com";
        String senderName = "Mercado Licha";
        String subject = "Por favor verifique su cuenta!";
        String content = "Querido [[name]],<br>"
                + "Por favor haga click en el enlace de abajo para verificar tu cuenta:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFICAR</a></h3>"
                + "Muchas gracias!,<br>"
                + "Mercado Licha.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstName());
        String verifyURL = siteURL + "/user/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
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
