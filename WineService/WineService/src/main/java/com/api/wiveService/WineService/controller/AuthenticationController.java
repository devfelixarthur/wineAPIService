package com.api.wiveService.WineService.controller;

import com.api.wiveService.WineService.domain.user.AuthenticationDTO;
import com.api.wiveService.WineService.domain.user.RegisterUserDTO;
import com.api.wiveService.WineService.domain.user.User;
import com.api.wiveService.WineService.domain.user.UserRole;
import com.api.wiveService.WineService.exceptions.WineException;
import com.api.wiveService.WineService.repository.UserRepository;
import com.api.wiveService.WineService.util.MsgCodWineApi;
import com.api.wiveService.WineService.util.ResponsePadraoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
     private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO login){
        var usernamePassword = new UsernamePasswordAuthenticationToken(login.email(), login.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponsePadraoDTO register(@RequestBody @Valid RegisterUserDTO data) {

        if (this.userRepository.findByEmail(data.email()) != null) {
            throw new WineException(new MsgCodWineApi().getCodigoErro(1), BAD_REQUEST);
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        UserRole role = UserRole.ADMIN;
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

        return ResponsePadraoDTO.sucesso("Cadastro realizado com sucesso!");
    }
}
