package com.api.wiveService.WineService.controller;

import com.api.wiveService.WineService.config.security.TokenService;
import com.api.wiveService.WineService.domain.user.dto.AuthenticationDTO;
import com.api.wiveService.WineService.domain.user.dto.PasswordRecoveryDTO;
import com.api.wiveService.WineService.domain.user.dto.RegisterUserDTO;
import com.api.wiveService.WineService.domain.user.bean.User;
import com.api.wiveService.WineService.domain.user.UserRole;
import com.api.wiveService.WineService.exceptions.WineException;
import com.api.wiveService.WineService.repository.UserRepository;
import com.api.wiveService.WineService.util.MsgCodWineApi;
import com.api.wiveService.WineService.util.ResponsePadraoDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/login")
    public ResponsePadraoDTO login(@RequestBody @Valid AuthenticationDTO login){
        var usernamePassword = new UsernamePasswordAuthenticationToken(login.email(), login.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);


        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponsePadraoDTO.sucesso(token);
    }

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

    @PostMapping("/recoveryPassword")
    public ResponsePadraoDTO recoverPassword(@RequestBody @Valid PasswordRecoveryDTO data) {

        User user = (User) userRepository.findByEmail(data.getEmail());

        if (user == null) {
            throw new WineException(new MsgCodWineApi().getCodigoErro(14), BAD_REQUEST);
        }

        if (!user.getNome().equalsIgnoreCase(data.getNome())) {
            throw new WineException(new MsgCodWineApi().getCodigoErro(15), BAD_REQUEST);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dtNascimento;
        try {
            dtNascimento = LocalDate.parse(data.getDtNascimento(), formatter);
        } catch (DateTimeParseException e) {
            throw new WineException(new MsgCodWineApi().getCodigoErro(16), BAD_REQUEST);
        }

        if (!user.getDtNascimento().isEqual(dtNascimento)) {
            throw new WineException(new MsgCodWineApi().getCodigoErro(16), BAD_REQUEST);
        }

        if (!data.getSenha().equals(data.getConfirmaSenha())) {
            throw new WineException(new MsgCodWineApi().getCodigoErro(17), BAD_REQUEST);
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(data.getSenha(), user.getPassword())) {
            throw new WineException(new MsgCodWineApi().getCodigoErro(18), BAD_REQUEST);
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getSenha());
        user.setSenha(encryptedPassword);
        userRepository.save(user);

        return ResponsePadraoDTO.sucesso("Senha atualizada com sucesso!");
    }
}
