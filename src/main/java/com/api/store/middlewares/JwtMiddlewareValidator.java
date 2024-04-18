package com.api.store.middlewares;


import com.api.store.utils.encryption.JwtTokenUtil;
import com.auth0.jwt.exceptions.JWTVerificationException;
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
        String requestUri = request.getRequestURI();
        boolean isSignInRouter = requestUri.startsWith("/user") && request.getMethod().equals("POST");
        boolean isAuthRouter = requestUri.startsWith("/auth");
        if (isAuthRouter || isSignInRouter) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = request.getHeader("Authorization");
        try {
            String userId = this.jwtTokenUtil.getTokenInfo(accessToken);
            request.setAttribute("userId", userId);
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException exception) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
        }
    }
}
