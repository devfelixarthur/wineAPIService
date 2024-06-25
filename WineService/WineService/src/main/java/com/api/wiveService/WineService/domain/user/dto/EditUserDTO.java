package com.api.wiveService.WineService.domain.user.dto;

import jakarta.validation.constraints.*;


public record EditUserDTO(
        @NotNull(message = "O Id é um campo obrigatório.")
        @DecimalMin(value="1", message = "O id deve ser um numero inteiro e positivo.")

        Long id,

        String nome,

        @Email(message = "Formato de e-mail inválido.")
        String email,

        String senha,

        String dtNascimento
) {
}
