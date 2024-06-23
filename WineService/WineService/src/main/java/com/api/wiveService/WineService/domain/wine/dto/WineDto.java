package com.api.wiveService.WineService.domain.wine.dto;

import com.api.wiveService.WineService.domain.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WineDto {
    private Long id;
    private String nome;
    private Pais pais;
    private String adega;
    private Integer safra;
    private String imagem;
    private String status;
    private Date dataCadastro;
}
