package com.api.wiveService.WineService.domain.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentsDTO {
    Long idComment;
    Long idUsuario;
    String nomeUsuario;
    String comentarios;
    LocalDateTime dataCadastro;
}
