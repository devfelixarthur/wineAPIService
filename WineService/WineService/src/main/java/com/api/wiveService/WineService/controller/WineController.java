package com.api.wiveService.WineService.controller;


import com.api.wiveService.WineService.domain.wine.dto.*;
import com.api.wiveService.WineService.service.wine.WineService;
import com.api.wiveService.WineService.util.ResponsePadraoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("wine")
public class WineController {

    @Autowired
    private WineService wineService;

    @PostMapping("/cadastrarWine")
    public ResponseEntity<ResponsePadraoDTO> cadastrarWine(@RequestBody @Valid CadastrarWineDTO form) {
        ResponsePadraoDTO responsePadraoDTO = wineService.cadastrarWine(form);
        return ResponseEntity.ok().body(responsePadraoDTO);
    }

    @PutMapping("/editarWine")
    public ResponseEntity<ResponsePadraoDTO> editWine(@RequestBody @Valid AlterarWineDTO form) {
        ResponsePadraoDTO responsePadraoDTO = wineService.alterarWine(form);
        return ResponseEntity.ok().body(responsePadraoDTO);
    }

    @GetMapping("/buscalAllWine")
    public ResponseEntity<ResponseWineDTO> getAllWines(
            @RequestParam(defaultValue = "1") int itemInicio,
            @RequestParam(defaultValue = "25") int itemFim) {
        ResponseWineDTO responseWinesDto = wineService.getAllWines(itemInicio, itemFim);
        return ResponseEntity.ok().body(responseWinesDto);
    }

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


    @PatchMapping("/alterarStatus")
    public ResponseEntity<ResponsePadraoDTO> alterarStatus(@RequestBody @Valid AlterarStatusWineDTO form) {
        ResponsePadraoDTO responsePadraoDTO = wineService.alterarStatus(form);
        return ResponseEntity.ok().body(responsePadraoDTO);
    }

    @GetMapping("/getCountries")
    public ResponseEntity<ResponseCountryDTO> getAllCountries() {
        ResponseCountryDTO responseCountriesDto = wineService.getAllCountries();
        return ResponseEntity.ok().body(responseCountriesDto);
    }

    @GetMapping("/getAdegas")
    public ResponseEntity<ResponseAdegaDTO> getAllAdegas() {
        ResponseAdegaDTO responseAdegasDto = wineService.getAllAdegas();
        return ResponseEntity.ok().body(responseAdegasDto);
    }

    @GetMapping("/getUvas")
    public ResponseEntity<ResponseUvasDTO> getAllUvas() {
        ResponseUvasDTO responseUvasDTO = wineService.getAllUvas();
        return ResponseEntity.ok().body(responseUvasDTO);
    }
}
