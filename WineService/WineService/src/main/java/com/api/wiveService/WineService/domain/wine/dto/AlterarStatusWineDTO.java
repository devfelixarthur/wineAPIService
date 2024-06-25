package com.api.wiveService.WineService.domain.wine.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlterarStatusWineDTO {
    @NotNull(message = "O campo id é obrigatório")
    @DecimalMin(value="1", message = "O campo id deve ser um numero inteiro e positivo")
    private Long id;

    @NotNull(message = "O campo status é obrigatório")
    @DecimalMin(value="0", message = "O campo status deve ser 0(Inativo) ou 1(Ativo)")
    @DecimalMax(value="1", message = "O campo status deve ser 0(Inativo) ou 1(Ativo)")
    private Integer status;


}
