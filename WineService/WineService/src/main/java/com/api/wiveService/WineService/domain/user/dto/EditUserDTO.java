package com.api.wiveService.WineService.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record EditUserDTO(
        @NotNull(message = "O Id é um campo obrigatório.")
        Long id,

        String nome,

        @Email(message = "Formato de e-mail inválido.")
        String email,

        String senha,

        String dtNascimento
) {
}
