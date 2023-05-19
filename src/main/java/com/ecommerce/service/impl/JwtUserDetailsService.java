package com.ecommerce.service.impl;
import com.ecommerce.DTO.UserDTO;
import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.utils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = userRepository.findUserByUsername(username);
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(userDTO.getRole().toString()));
        return new User(userDTO.getUsername(), userDTO.getPassword(), authorityList);
    }

    public Boolean validateToken(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if (StringUtils.isNotEmpty(username)) {
            UserDetails userDetails = loadUserByUsername(username);
            System.out.println(jwtTokenUtil.validateToken(token, userDetails));
            return jwtTokenUtil.validateToken(token, userDetails);
        }
        return false;
    }
}