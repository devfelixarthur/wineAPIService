package com.api.wiveService.WineService.domain.avaliacao.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAvaliacaoDTO {

    @NotNull(message = "O idAvaliacao do usuário é obrigatório.")
    @DecimalMin(value="1", message = "O idAvaliacao deve ser um numero inteiro e positivo.")
    private Long idAvaliacao;

    @NotNull(message = "A avaliação é obrigatória.")
    @Min(value = 1, message = "A avaliação deve ser no mínimo 1.")
    @Max(value = 5, message = "A avaliação deve ser no máximo 5.")
    private Integer avaliacao;

}
