package com.api.wiveService.WineService.controller;


import com.api.wiveService.WineService.domain.user.dto.AlterarStatusUserDTO;
import com.api.wiveService.WineService.domain.user.dto.EditUserDTO;
import com.api.wiveService.WineService.domain.user.dto.ResponseUserDTO;
import com.api.wiveService.WineService.service.user.UserService;
import com.api.wiveService.WineService.util.ResponsePadraoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@Tag(name = "Usuários", description = "Operaçãoes relacionadas a usuários do sistema.")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Operation(summary = "Editar Usuário", description = "Endpoint responsável pelo edição de cadastro de um usuário existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário Alterado com Sucesso.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados Inválidos", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro na solicitação.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class)))
    })
    @PutMapping("editarUser")
    public ResponseEntity editarUser(@RequestBody @Valid EditUserDTO form) {
        ResponsePadraoDTO responsePadraoDTO = userService.atualizarUser(form);
        return ResponseEntity.ok().body(responsePadraoDTO);
    }

    @Operation(summary = "Delete Usuário", description = "Endpoint responsável pela exclusão de um usuário existente. Apenas Usuários com a ROLE ADMIN possuem permissão para executar esta operação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário Excluído com Sucesso.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados Inválidos", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro na solicitação.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class)))
    })
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<ResponsePadraoDTO> deleteUser(@PathVariable Long userId) {
        ResponsePadraoDTO responsePadraoDTO = userService.deleteUser(userId);
        return ResponseEntity.ok().body(responsePadraoDTO);
    }

    @Operation(summary = "Alterar Status do Usuário", description = "Endpoint responsável pela alteração de status de um usuário existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário Excluído com Sucesso.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados Inválidos", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro na solicitação.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class)))
    })
    @PatchMapping("/alterarStatus")
    public ResponseEntity<ResponsePadraoDTO> alterarStatus(@RequestBody @Valid AlterarStatusUserDTO form) {
        ResponsePadraoDTO responsePadraoDTO = userService.alterarStatus(form);
        return ResponseEntity.ok().body(responsePadraoDTO);
    }

    @Operation(summary = "Busca todos os usuários cadastrados", description = "Endpoint responsável por buscar todos os usuários cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário Excluído com Sucesso.", content = @Content(schema = @Schema(implementation = ResponseUserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados Inválidos", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro na solicitação.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class)))
    })
    @GetMapping("/buscarTodosUsuarios")
    public ResponseEntity<ResponseUserDTO> getAllUsers(
            @RequestParam(defaultValue = "1") Integer itemInicio,
            @RequestParam(defaultValue = "25") Integer itemFim) {
        ResponseUserDTO responseUserDto = userService.getAllUsers(itemInicio, itemFim);
        return ResponseEntity.ok().body(responseUserDto);
    }
}
