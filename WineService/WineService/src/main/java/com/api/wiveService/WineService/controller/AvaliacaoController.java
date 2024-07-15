package com.api.wiveService.WineService.controller;

import com.api.wiveService.WineService.domain.avaliacao.dto.CreateAvaliacaoDTO;
import com.api.wiveService.WineService.domain.avaliacao.dto.UpdateAvaliacaoDTO;
import com.api.wiveService.WineService.domain.avaliacao.dto.AvaliacaoMediaDTO;
import com.api.wiveService.WineService.service.avaliacao.AvaliacaoService;
import com.api.wiveService.WineService.util.ResponsePadraoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @PostMapping("/createAvaliation")
    public ResponseEntity<ResponsePadraoDTO> createAvaliacao(@RequestBody @Valid CreateAvaliacaoDTO createAvaliacaoDTO) {
        var avaliacao = avaliacaoService.createAvaliacao(createAvaliacaoDTO);
        return ResponseEntity.ok(avaliacao);
    }

    @PutMapping("/updateAvaliacao")
    public ResponseEntity<ResponsePadraoDTO> updateAvaliacao(@RequestBody @Valid UpdateAvaliacaoDTO updateAvaliacaoDTO) {
        ResponsePadraoDTO avaliacao = avaliacaoService.updateAvaliacao(updateAvaliacaoDTO);
        return ResponseEntity.ok(avaliacao);
    }

    @GetMapping("/avaliacaoByWine")
    public ResponseEntity<AvaliacaoMediaDTO> getMediaAvaliacoes(@RequestParam(required = true) Long wineId) {
        AvaliacaoMediaDTO avaliacaoMediaDTO = avaliacaoService.getAvalicaoWine(wineId);
        return ResponseEntity.ok(avaliacaoMediaDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AvaliacaoMediaDTO>> getAllAvaliacoes() {
        List<AvaliacaoMediaDTO> allMedias = avaliacaoService.getAllAvaliacoesMedia();
        return ResponseEntity.ok(allMedias);
    }
}
