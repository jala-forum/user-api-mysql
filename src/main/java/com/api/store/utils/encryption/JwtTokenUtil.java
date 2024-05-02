package com.api.store.utils.encryption;

import com.api.store.model.entities.mongodb.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class JwtTokenUtil {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            List<String> permissions = new ArrayList<>();
            permissions.add("ROLE_USER");
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("parkingcontrol-api")
                    .withSubject(user.getId())
                    .withClaim("roles", permissions)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro while generate token", exception);
        }
    }

    public Map<java.lang.String, Claim> getTokenClaims(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer("parkingcontrol-api")
                .build()
                .verify(token)
                .getClaims();
    }

    public String getTokenSubject(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer("parkingcontrol-api")
                .build()
                .verify(token)
                .getSubject();
    }
    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public boolean verify(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        try {
            JWT.require(algorithm)
                    .withIssuer("parkingcontrol-api")
                    .build()
                    .verify(token);

            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }
}
