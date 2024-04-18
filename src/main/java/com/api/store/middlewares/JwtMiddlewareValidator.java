package com.api.store.middlewares;


import com.api.store.utils.encryption.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JwtMiddlewareValidator extends OncePerRequestFilter {
    public final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public JwtMiddlewareValidator(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader("Authorization");

        System.out.println(this.jwtTokenUtil.getTokenInfo(accessToken));
        filterChain.doFilter(request, response);
    }
}
