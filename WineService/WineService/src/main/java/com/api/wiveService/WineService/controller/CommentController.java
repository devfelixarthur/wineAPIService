package com.api.wiveService.WineService.controller;

import com.api.wiveService.WineService.domain.comments.dto.*;
import com.api.wiveService.WineService.domain.wine.dto.CadastrarWineDTO;
import com.api.wiveService.WineService.domain.wine.dto.ResponseWineDTO;
import com.api.wiveService.WineService.service.comments.CommentService;
import com.api.wiveService.WineService.service.wine.WineService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comments")
@Tag(name = "Comentários", description = "Operaçãoes relacionadas a comentários dos vinhos.")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Operation(summary = "Cadatrar Comentário", description = "Endpoint responsável pelo cadastro de um novo comentário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentário cadastrada com Sucesso.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados Inválidos", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro na solicitação.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class)))
    })
    @PostMapping("/cadastrarComment")
    public ResponseEntity<ResponsePadraoDTO> cadatrarComment(@RequestBody @Valid CreateCommentDTO form) {
        ResponsePadraoDTO responsePadraoDTO = commentService.RegisterComment(form);
        return ResponseEntity.ok().body(responsePadraoDTO);
    }

    @Operation(summary = "Editar Comentário", description = "Endpoint responsável pela edição de um comentário existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentário cadastrada com Sucesso.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados Inválidos", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro na solicitação.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class)))
    })
    @PutMapping("/editarComment")
    public ResponseEntity<ResponsePadraoDTO> cadatrarComment(@RequestBody @Valid EditCommentDTO form) {
        ResponsePadraoDTO responsePadraoDTO = commentService.EditarComment(form);
        return ResponseEntity.ok().body(responsePadraoDTO);
    }

    @Operation(summary = "Alterar Status Comentário", description = "Endpoint resposável pela alteração de status de um comentário existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status Alterado com Sucesso.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados Inválidos", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro na solicitação.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class)))
    })
    @PatchMapping("/alterarStatusComment")
    public ResponseEntity<ResponsePadraoDTO> cadatrarComment(@RequestBody @Valid AlterarStatusDTO form) {
        ResponsePadraoDTO responsePadraoDTO = commentService.AlterarStatus(form);
        return ResponseEntity.ok().body(responsePadraoDTO);
    }

    @Operation(summary = "Buscar Todos os Comentários", description = "Endpoint resposável por buscar todos os comentários existentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status Alterado com Sucesso.", content = @Content(schema = @Schema(implementation = ResponseCommentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados Inválidos", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro na solicitação.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class)))
    })
    @GetMapping("/getAllComments")
    public ResponseEntity<ResponseCommentDTO> getAllComments(
            @RequestParam(defaultValue = "1") Integer itemInicio,
            @RequestParam(defaultValue = "25") Integer itemFim) {
        ResponseCommentDTO responseCommentDTO = commentService.getAllComments(itemInicio, itemFim);
        return ResponseEntity.ok().body(responseCommentDTO);
    }

    @Operation(summary = "Buscar Todos os Comentários de um Vinho", description = "Endpoint resposável por buscar todos os comentários existentes para um vinho.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status Alterado com Sucesso.", content = @Content(schema = @Schema(implementation = ResponseCommentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados Inválidos", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados não encontrados", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro na solicitação.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class)))
    })
    @GetMapping("/commentsByWine")
    public ResponseEntity<ResponseCommentDTO> getCommentsByWine(
            @RequestParam(defaultValue = "1") Integer itemInicio,
            @RequestParam(defaultValue = "25") Integer itemFim,
            @RequestParam(required = true) Long wineId) {
        ResponseCommentDTO responseCommentDTO = commentService.getCommentsByWine(itemInicio, itemFim, wineId);
        return ResponseEntity.ok().body(responseCommentDTO);
    }
}
