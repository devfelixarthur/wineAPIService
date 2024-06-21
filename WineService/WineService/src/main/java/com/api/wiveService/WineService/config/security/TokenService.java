package com.api.wiveService.WineService.config.security;

import com.api.wiveService.WineService.domain.user.User;
import com.api.wiveService.WineService.exceptions.WineException;
import com.api.wiveService.WineService.util.ResponsePadraoDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;


    public String generateToken(User user){

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generatedExpirationDate())
                    .sign(algorithm);
            return token;
        }catch (JWTCreationException exception) {
            throw new WineException("Falha na Geração do Token", exception.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    public String validateToken (String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new WineException("Token Inválido", exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private Instant generatedExpirationDate(){
        return LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.of("-03:00"));
    }
}
