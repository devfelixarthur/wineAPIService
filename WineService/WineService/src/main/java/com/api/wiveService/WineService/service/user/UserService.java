package com.api.wiveService.WineService.service.user;

import com.api.wiveService.WineService.config.security.TokenService;
import com.api.wiveService.WineService.domain.user.bean.User;
import com.api.wiveService.WineService.domain.user.dto.AlterarStatusUserDTO;
import com.api.wiveService.WineService.domain.user.dto.EditUserDTO;
import com.api.wiveService.WineService.domain.user.dto.ResponseUserDTO;
import com.api.wiveService.WineService.domain.user.dto.UserDTO;
import com.api.wiveService.WineService.exceptions.WineException;
import com.api.wiveService.WineService.repository.UserRepository;
import com.api.wiveService.WineService.util.MsgCodWineApi;
import com.api.wiveService.WineService.util.ResponsePadraoDTO;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);


    public ResponsePadraoDTO atualizarUser(EditUserDTO form) {

        Optional<User> userOptional = Optional.ofNullable(userRepository.findById(form.id()));

        if (userOptional.isEmpty()) {
            throw new WineException(new MsgCodWineApi().getCodigoErro(4), HttpStatus.BAD_REQUEST);
        }

        User user = userOptional.get();

        if (form.email() != null && !form.email().isEmpty()) {
            Long emailCadastrado = userRepository.countEmailUsed(form.email(), form.id());
            if (emailCadastrado > 0) {
                throw new WineException(new MsgCodWineApi().getCodigoErro(5), HttpStatus.BAD_REQUEST);
            }
            user.setEmail(form.email());
        }

        if (form.nome() != null && !form.nome().isEmpty()) {
            user.setNome(form.nome());
        }
        if (form.email() != null && !form.email().isEmpty()) {
            user.setEmail(form.email());
        }
        if (form.senha() != null && !form.senha().isEmpty()) {
            String encryptedPassword = passwordEncoder.encode(form.senha());
            user.setSenha(encryptedPassword);
        }
        if (form.dtNascimento() != null && !form.dtNascimento().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            try {
                LocalDate dtNascimento = LocalDate.parse(form.dtNascimento(), formatter);
                user.setDtNascimento(dtNascimento);
            } catch (DateTimeParseException e) {
                throw new WineException("Data de nascimento inválida. Use o formato dd/MM/yyyy.", HttpStatus.BAD_REQUEST);
            }
        }

        userRepository.save(user);
        logger.info("----------------------INICIO LOGGER INFO:----------------------");
        logger.info("Usuário Atualizado com sucesso: {} ", user);
        logger.info("----------------------FIM LOGGER INFO:----------------------");
        return ResponsePadraoDTO.sucesso("Usuário Atualizado com sucesso");
    }

    @Transactional
    public ResponsePadraoDTO deleteUser(Long id) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = null;

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            email = userDetails.getUsername();
        }

        User user = (User) userRepository.findByEmail(email);

        String role = String.valueOf(user.getRole());

        if (!role.equals("ADMIN")) {
            throw new WineException(new MsgCodWineApi().getCodigoErro(6), HttpStatus.FORBIDDEN);
        }

        Optional<User> userExiste = userRepository.findUserById(id);

        if(userExiste == null){
            throw new WineException(new MsgCodWineApi().getCodigoErro(4), HttpStatus.NOT_FOUND);
        }

        userRepository.deleteById(id);
        logger.info("----------------------INICIO LOGGER INFO:----------------------");
        logger.info("Usuário Deletado com sucesso: " + email);
        logger.info("----------------------FIM LOGGER INFO:----------------------");
        return ResponsePadraoDTO.sucesso("Usuário Deletado com Sucesso.");
    }

    public ResponsePadraoDTO alterarStatus(AlterarStatusUserDTO form) {

        Optional<User> userOptional = Optional.ofNullable(userRepository.findById(form.getId()));

        if (userOptional.isEmpty()) {
            throw new WineException(new MsgCodWineApi().getCodigoErro(4), HttpStatus.BAD_REQUEST);
        }

        User user = userOptional.get();
        String novoStatus = form.getStatus().equals(0) ? "Inativo" : "Ativo";

        if (user.getStatus().equals(novoStatus)) {
            throw new WineException("Usuário já encontra-se " + novoStatus.toLowerCase(), HttpStatus.BAD_REQUEST);
        }

        user.setStatus(novoStatus);
        userRepository.save(user);
        logger.info("----------------------INICIO LOGGER INFO:----------------------");
        logger.info("Status do usuário " + user.getEmail() + " alterado para " + novoStatus);
        logger.info("----------------------FIM LOGGER INFO:----------------------");
        return ResponsePadraoDTO.sucesso("Status do usuário alterado para " + novoStatus);
    }

    public ResponseUserDTO getAllUsers(int itemInicio, int itemFim) {
        if (itemInicio < 1){
            throw new WineException(new MsgCodWineApi().getCodigoErro(7), HttpStatus.BAD_REQUEST);
        }
        if (itemFim < 1){
            throw new WineException(new MsgCodWineApi().getCodigoErro(8), HttpStatus.BAD_REQUEST);
        }
        if (itemInicio > itemFim){
            throw new WineException(new MsgCodWineApi().getCodigoErro(9), HttpStatus.BAD_REQUEST);
        }

        int page = (itemInicio - 1) / itemFim;
        int size = itemFim;

        Page<User> userPage = userRepository.findAllUsers(PageRequest.of(page, size));
        Long totalUsers = userPage.getTotalElements();
        List<UserDTO> listUserDTO = userPage.getContent().stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());

        return new ResponseUserDTO(totalUsers, listUserDTO);
    }

    private UserDTO convertToUserDTO(User user) {
        return new UserDTO(user.getId(), user.getNome(), user.getEmail(), user.getStatus(), user.getRole());
    }
}
