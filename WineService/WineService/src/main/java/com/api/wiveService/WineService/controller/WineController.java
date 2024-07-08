package com.api.wiveService.WineService.controller;

import com.api.wiveService.WineService.domain.user.dto.AlterarStatusUserDTO;
import com.api.wiveService.WineService.domain.user.dto.ResponseUserDTO;
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
}
