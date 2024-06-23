package com.api.wiveService.WineService.config.security;

import com.api.wiveService.WineService.domain.user.bean.User;
import com.api.wiveService.WineService.exceptions.WineException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);


    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generatedExpirationDate())
                    .sign(algorithm);

            logger.info("----------------------INICIO LOGGER INFO:----------------------");
            logger.info("Token gerado com sucesso para o usuário: {}", user.getEmail());
            logger.info("----------------------FIM LOGGER INFO:----------------------");
            return token;
        } catch (JWTCreationException exception) {
            logger.info("----------------------INICIO LOGGER INFO:----------------------");
            logger.info("Falha generateToken: ", exception.getMessage());
            logger.info("----------------------FIM LOGGER INFO:----------------------");
            throw new WineException("Falha na Geração do Token", exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            logger.info("----------------------INICIO LOGGER INFO:----------------------");
            logger.info("Falha validateToken: " + exception.getMessage());
            logger.info("----------------------FIM LOGGER INFO:----------------------");
            throw new WineException("Token Inválido", exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private Instant generatedExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
