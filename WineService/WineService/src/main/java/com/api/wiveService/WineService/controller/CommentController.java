package com.api.wiveService.WineService.controller;

import com.api.wiveService.WineService.domain.comments.dto.*;
import com.api.wiveService.WineService.domain.wine.dto.CadastrarWineDTO;
import com.api.wiveService.WineService.domain.wine.dto.ResponseWineDTO;
import com.api.wiveService.WineService.service.comments.CommentService;
import com.api.wiveService.WineService.service.wine.WineService;
import com.api.wiveService.WineService.util.ResponsePadraoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/cadastrarComment")
    public ResponseEntity<ResponsePadraoDTO> cadatrarComment(@RequestBody @Valid CreateCommentDTO form) {
        ResponsePadraoDTO responsePadraoDTO = commentService.RegisterComment(form);
        return ResponseEntity.ok().body(responsePadraoDTO);
    }

    @PutMapping("/editarComment")
    public ResponseEntity<ResponsePadraoDTO> cadatrarComment(@RequestBody @Valid EditCommentDTO form) {
        ResponsePadraoDTO responsePadraoDTO = commentService.EditarComment(form);
        return ResponseEntity.ok().body(responsePadraoDTO);
    }

    @PatchMapping("/alterarStatusComment")
    public ResponseEntity<ResponsePadraoDTO> cadatrarComment(@RequestBody @Valid AlterarStatusDTO form) {
        ResponsePadraoDTO responsePadraoDTO = commentService.AlterarStatus(form);
        return ResponseEntity.ok().body(responsePadraoDTO);
    }

    @GetMapping("/getAllComments")
    public ResponseEntity<ResponseCommentDTO> getAllComments(
            @RequestParam(defaultValue = "1") Integer itemInicio,
            @RequestParam(defaultValue = "25") Integer itemFim) {
        ResponseCommentDTO responseCommentDTO = commentService.getAllComments(itemInicio, itemFim);
        return ResponseEntity.ok().body(responseCommentDTO);
    }

    @GetMapping("/commentsByWine")
    public ResponseEntity<ResponseCommentDTO> getCommentsByWine(
            @RequestParam(defaultValue = "1") Integer itemInicio,
            @RequestParam(defaultValue = "25") Integer itemFim,
            @RequestParam(required = true) Long wineId) {
        ResponseCommentDTO responseCommentDTO = commentService.getCommentsByWine(itemInicio, itemFim, wineId);
        return ResponseEntity.ok().body(responseCommentDTO);
    }
}
