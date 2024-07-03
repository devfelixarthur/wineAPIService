package com.api.wiveService.WineService.controller;

import com.api.wiveService.WineService.config.security.TokenService;
import com.api.wiveService.WineService.domain.user.dto.AuthenticationDTO;
import com.api.wiveService.WineService.domain.user.dto.RegisterUserDTO;
import com.api.wiveService.WineService.domain.user.bean.User;
import com.api.wiveService.WineService.domain.user.UserRole;
import com.api.wiveService.WineService.exceptions.WineException;
import com.api.wiveService.WineService.repository.UserRepository;
import com.api.wiveService.WineService.util.MsgCodWineApi;
import com.api.wiveService.WineService.util.ResponsePadraoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("auth")

public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);


    @Operation(summary = "Login", description = "Login para autenticação e acesso a outros endpoints do sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class)))
    })
    @PostMapping("/login")
    public ResponsePadraoDTO login(@RequestBody @Valid AuthenticationDTO login) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(login.email(), login.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);


        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponsePadraoDTO.sucesso(token);
    }


    @Operation(summary = "Registro de Usuário", description = "Endpoint para registro de um usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class)))
    })
    @PostMapping("/register")
    public ResponsePadraoDTO register(@RequestBody @Valid RegisterUserDTO data) {

        if (this.userRepository.findByEmail(data.email()) != null) {
            throw new WineException(new MsgCodWineApi().getCodigoErro(1), BAD_REQUEST);
        }


        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        UserRole role = UserRole.USER;
        String status = "Ativo";


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dtNascimento;

        try {
            dtNascimento = LocalDate.parse(data.dtNascimento(), formatter);
        } catch (DateTimeParseException e) {
            throw new WineException(new MsgCodWineApi().getCodigoErro(2), BAD_REQUEST);
        }

        if (dtNascimento.isAfter(LocalDate.now())) {
            throw new WineException(new MsgCodWineApi().getCodigoErro(3), BAD_REQUEST);
        }

        User newUser = new User(data.nome(), data.email(), encryptedPassword, role, dtNascimento, status);
        this.userRepository.save(newUser);

        logger.info("Usuário Cadastrado com sucesso: {} ", newUser);
        return ResponsePadraoDTO.sucesso("Cadastro realizado com sucesso!");
    }
}
