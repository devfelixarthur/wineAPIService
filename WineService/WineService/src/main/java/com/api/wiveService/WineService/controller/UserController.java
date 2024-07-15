package com.api.wiveService.WineService.controller;


import com.api.wiveService.WineService.domain.user.dto.AlterarStatusUserDTO;
import com.api.wiveService.WineService.domain.user.dto.EditUserDTO;
import com.api.wiveService.WineService.domain.user.dto.ResponseUserDTO;
import com.api.wiveService.WineService.service.user.UserService;
import com.api.wiveService.WineService.util.ResponsePadraoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PutMapping("editarUser")
    public ResponseEntity editarUser(@RequestBody @Valid EditUserDTO form) {
        ResponsePadraoDTO responsePadraoDTO = userService.atualizarUser(form);
        return ResponseEntity.ok().body(responsePadraoDTO);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<ResponsePadraoDTO> deleteUser(@PathVariable Long userId) {
        ResponsePadraoDTO responsePadraoDTO = userService.deleteUser(userId);
        return ResponseEntity.ok().body(responsePadraoDTO);
    }

    @PatchMapping("/alterarStatus")
    public ResponseEntity<ResponsePadraoDTO> alterarStatus(@RequestBody @Valid AlterarStatusUserDTO form) {
        ResponsePadraoDTO responsePadraoDTO = userService.alterarStatus(form);
        return ResponseEntity.ok().body(responsePadraoDTO);
    }

    @GetMapping("/buscarTodosUsuarios")
    public ResponseEntity<ResponseUserDTO> getAllUsers(
            @RequestParam(defaultValue = "1") Integer itemInicio,
            @RequestParam(defaultValue = "25") Integer itemFim) {
        ResponseUserDTO responseUserDto = userService.getAllUsers(itemInicio, itemFim);
        return ResponseEntity.ok().body(responseUserDto);
    }
}
