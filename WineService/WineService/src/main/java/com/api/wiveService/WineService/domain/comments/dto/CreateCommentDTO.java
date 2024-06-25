package com.api.wiveService.WineService.domain.comments.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCommentDTO(
        @NotNull(message = "O IdWine é um campo obrigatório.")
        @Min(value = 1, message = "O IdWine deve ser maior que 0.")
        Long idWine,

        @NotNull(message = "O IdUser é um campo obrigatório.")

        @Min(value = 1, message = "O IdUser deve ser maior que 0.")
        Long idUser,

        @NotBlank(message = "O campo message é um campo obrigatório e não pode estar em branco.")
        @Size(min = 3, message = "O campo message deve ter pelo menos 3 caracteres.")
        String message
) {
}
