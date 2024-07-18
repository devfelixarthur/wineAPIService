package com.api.wiveService.WineService.controller;


import com.api.wiveService.WineService.domain.wine.dto.*;
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

@RestController
@RequestMapping("wine")
@Tag(name = "Vinhos", description = "Operaçãoes relacionadas aos vinhos do sistema.")
public class WineController {

    @Autowired
    private WineService wineService;

    @Operation(summary = "Cadastrar Vinho", description = "Endpoint responsável pelo cadastro de um novo vinho.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro Realizado com Sucesso.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados Inválidos", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro na solicitação.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class)))
    })
    @PostMapping("/cadastrarWine")
    public ResponseEntity<ResponsePadraoDTO> cadastrarWine(@RequestBody @Valid CadastrarWineDTO form) {
        ResponsePadraoDTO responsePadraoDTO = wineService.cadastrarWine(form);
        return ResponseEntity.ok().body(responsePadraoDTO);
    }

    @Operation(summary = "Editar Vinho", description = "Endpoint responsável pela edição no cadastro de um vinho existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro Realizado com Sucesso.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados Inválidos.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados não encontrados.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro na solicitação.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class)))
    })
    @PutMapping("/editarWine")
    public ResponseEntity<ResponsePadraoDTO> editWine(@RequestBody @Valid AlterarWineDTO form) {
        ResponsePadraoDTO responsePadraoDTO = wineService.alterarWine(form);
        return ResponseEntity.ok().body(responsePadraoDTO);
    }

    @Operation(summary = "Buscar Todos os Vinhos", description = "Endpoint responsável pela busca de todos os vinhos existentes no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro Realizado com Sucesso.", content = @Content(schema = @Schema(implementation = ResponseWineDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados Inválidos.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro na solicitação.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class)))
    })
    @GetMapping("/buscalAllWine")
    public ResponseEntity<ResponseWineDTO> getAllWines(
            @RequestParam(defaultValue = "1") int itemInicio,
            @RequestParam(defaultValue = "25") int itemFim) {
        ResponseWineDTO responseWinesDto = wineService.getAllWines(itemInicio, itemFim);
        return ResponseEntity.ok().body(responseWinesDto);
    }

    @Operation(summary = "Buscar Todos os Vinhos Por Parâmetros", description = "Endpoint responsável pela busca de todos os vinhos existentes no sistema com filtros.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro Realizado com Sucesso.", content = @Content(schema = @Schema(implementation = ResponseWineDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados Inválidos.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro na solicitação.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class)))
    })
    @GetMapping("/buscaWineByFields")
    public ResponseEntity<ResponseWineDTO> getAllWines(
            @RequestParam(defaultValue = "1", required = false) int itemInicio,
            @RequestParam(defaultValue = "25", required = false) int itemFim,
            @RequestParam(required = false) String wineName,
            @RequestParam(required = false) String pais,
            @RequestParam(required = false) String uva,
            @RequestParam(required = false) String adega) {
        ResponseWineDTO responseWinesDto = wineService.getWinesByFields(itemInicio, itemFim, wineName, pais, uva, adega);
        return ResponseEntity.ok().body(responseWinesDto);
    }

    @Operation(summary = "Alterar status do Vinho", description = "Endpoint responsável pela alteração do status de um vinho existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro Realizado com Sucesso.", content = @Content(schema = @Schema(implementation = ResponseWineDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados Inválidos.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro na solicitação.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class)))
    })
    @PatchMapping("/alterarStatus")
    public ResponseEntity<ResponsePadraoDTO> alterarStatus(@RequestBody @Valid AlterarStatusWineDTO form) {
        ResponsePadraoDTO responsePadraoDTO = wineService.alterarStatus(form);
        return ResponseEntity.ok().body(responsePadraoDTO);
    }

    @Operation(summary = "Buscar Países", description = "Endpoint responsável por buscar lista de países existentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com Sucesso.", content = @Content(schema = @Schema(implementation = ResponseCountryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados Inválidos.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro na solicitação.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class)))
    })
    @GetMapping("/getCountries")
    public ResponseEntity<ResponseCountryDTO> getAllCountries() {
        ResponseCountryDTO responseCountriesDto = wineService.getAllCountries();
        return ResponseEntity.ok().body(responseCountriesDto);
    }

    @Operation(summary = "Buscar Adegas", description = "Endpoint responsável por buscar lista de adegas existentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com Sucesso.", content = @Content(schema = @Schema(implementation = ResponseAdegaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados Inválidos.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro na solicitação.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class)))
    })
    @GetMapping("/getAdegas")
    public ResponseEntity<ResponseAdegaDTO> getAllAdegas() {
        ResponseAdegaDTO responseAdegasDto = wineService.getAllAdegas();
        return ResponseEntity.ok().body(responseAdegasDto);
    }

    @Operation(summary = "Buscar Uvas", description = "Endpoint responsável por buscar lista de uvas existentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com Sucesso.", content = @Content(schema = @Schema(implementation = ResponseUvasDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados Inválidos.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro na solicitação.", content = @Content(schema = @Schema(implementation = ResponsePadraoDTO.class)))
    })
    @GetMapping("/getUvas")
    public ResponseEntity<ResponseUvasDTO> getAllUvas() {
        ResponseUvasDTO responseUvasDTO = wineService.getAllUvas();
        return ResponseEntity.ok().body(responseUvasDTO);
    }
}
