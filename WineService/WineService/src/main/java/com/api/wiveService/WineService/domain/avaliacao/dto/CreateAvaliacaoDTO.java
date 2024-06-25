package com.api.wiveService.WineService.domain.avaliacao.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateAvaliacaoDTO(
        @NotNull(message = "O ID do usuário é obrigatório.")
        @DecimalMin(value="1", message = "O idUsuario deve ser um numero inteiro e positivo.")
        Long idUsuario,

        @NotNull(message = "O ID do vinho é obrigatório.")
        @DecimalMin(value="1", message = "O idWine deve ser um numero inteiro e positivo.")
        Long idWine,

        @NotNull(message = "A avaliação é obrigatória.")
        @Min(value = 1, message = "A avaliação deve ser no mínimo 1.")
        @Max(value = 5, message = "A avaliação deve ser no máximo 5.")
        Integer avaliacao
) {}
