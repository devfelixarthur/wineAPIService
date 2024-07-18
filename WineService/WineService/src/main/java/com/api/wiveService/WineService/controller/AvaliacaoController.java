package com.api.wiveService.WineService.controller;

import com.api.wiveService.WineService.domain.avaliacao.dto.CreateAvaliacaoDTO;
import com.api.wiveService.WineService.domain.avaliacao.dto.UpdateAvaliacaoDTO;
import com.api.wiveService.WineService.domain.avaliacao.dto.AvaliacaoMediaDTO;
import com.api.wiveService.WineService.service.avaliacao.AvaliacaoService;
import com.api.wiveService.WineService.util.ResponsePadraoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@RequestMapping("/avaliacoes")
@Tag(name = "Avaliações", description = "Operaçãoes de Avaliações e classificação de vinhos.")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Operation(summary = "Criar Avaliação", description = "Endpoint responsável pelo cadastro de uma nova avaliação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação cadastrada com Sucesso.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados Inválidos", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro na solicitação.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class)))
    })
    @PostMapping("/createAvaliation")
    public ResponseEntity<ResponsePadraoDTO> createAvaliacao(@RequestBody @Valid CreateAvaliacaoDTO createAvaliacaoDTO) {
        var avaliacao = avaliacaoService.createAvaliacao(createAvaliacaoDTO);
        return ResponseEntity.ok(avaliacao);
    }

    @Operation(summary = "Alterar/Atualizar Avaliação", description = "Endpoint responsável pela atualização de uma avaliação existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação alterada com Sucesso.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados Inválidos", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro na solicitação.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class)))
    })
    @PutMapping("/updateAvaliacao")
    public ResponseEntity<ResponsePadraoDTO> updateAvaliacao(@RequestBody @Valid UpdateAvaliacaoDTO updateAvaliacaoDTO) {
        ResponsePadraoDTO avaliacao = avaliacaoService.updateAvaliacao(updateAvaliacaoDTO);
        return ResponseEntity.ok(avaliacao);
    }

    @Operation(summary = "Buscar Avaliação por WineId", description = "Endpoint responsável pela busca de todas as avaliações para um idWine.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação encontradas com Sucesso.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AvaliacaoMediaDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Dados Inválidos", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Vinho não encontrado", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro na solicitação.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class)))
    })
    @GetMapping("/avaliacaoByWine")
    public ResponseEntity<AvaliacaoMediaDTO> getMediaAvaliacoes(@RequestParam(required = true) Long wineId) {
        AvaliacaoMediaDTO avaliacaoMediaDTO = avaliacaoService.getAvalicaoWine(wineId);
        return ResponseEntity.ok(avaliacaoMediaDTO);
    }

    @Operation(summary = "Buscar todas as Avaliação", description = "Endpoint responsável por buscar todas as avaliações de todos os vinhos cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação encontradas com Sucesso.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AvaliacaoMediaDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Dados Inválidos", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro na solicitação.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class)))
    })
    @GetMapping("/all")
    public ResponseEntity<List<AvaliacaoMediaDTO>> getAllAvaliacoes() {
        List<AvaliacaoMediaDTO> allMedias = avaliacaoService.getAllAvaliacoesMedia();
        return ResponseEntity.ok(allMedias);
    }
}
