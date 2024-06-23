package com.api.wiveService.WineService.domain.user.dto;


import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlterarStatusUserDTO {

    @NotNull(message = "O campo id é obrigatório")
    @DecimalMin(value="1", message = "O campo id deve ser um numero inteiro e positivo")
    private Long id;

    @NotNull(message = "O campo id é obrigatório")
    @DecimalMin(value="0", message = "O campo status deve ser 0(Inativo) ou 1(Ativo)")
    @DecimalMax(value="1", message = "O campo status deve ser 0(Inativo) ou 1(Ativo)")
    private Integer status;


}
