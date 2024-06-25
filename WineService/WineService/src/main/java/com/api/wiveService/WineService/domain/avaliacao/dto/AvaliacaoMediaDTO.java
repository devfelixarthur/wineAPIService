package com.api.wiveService.WineService.domain.avaliacao.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoMediaDTO {
    private Long idVinho;
    private String nomeVinho;
    private Double avaliacaoMedia;
}
