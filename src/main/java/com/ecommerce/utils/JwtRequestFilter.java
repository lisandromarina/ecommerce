package com.ecommerce.utils;

import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.service.impl.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
            String requestTokenHeader = request.getHeader("Authorization");
            if (StringUtils.startsWith(requestTokenHeader, "Bearer ")) {
                String jwtToken = requestTokenHeader.substring(7);
                try {
                    String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                    if (StringUtils.isNotEmpty(username)
                            && null == SecurityContextHolder.getContext().getAuthentication()) {

                        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

                        if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                    new UsernamePasswordAuthenticationToken(
                                            userDetails, null, userDetails.getAuthorities());

                            usernamePasswordAuthenticationToken
                                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                            SecurityContextHolder.getContext()
                                    .setAuthentication(usernamePasswordAuthenticationToken);
                        }
                    }
                } catch (IllegalArgumentException e) {
                    logger.error("Unable to fetch JWT Token");
                } catch (ExpiredJwtException e) {
                    logger.error("JWT Token is expired");
                } catch (Exception e) {
                    throw new ApiRequestException(e.getMessage(), e);
                }
            } else {
                logger.warn("JWT Token does not begin with Bearer String");
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

}