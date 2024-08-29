package com.api.wiveService.WineService.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO (

        @Email(message = "Forneeça um email válido.")
        @Schema(description = "Email do usuário", example = "teste@swagger.com.br")
        String email,

        @NotBlank (message = "O campo password é obrigatório e não pode ser null")
        @Schema(description = "Email do usuário", example = "teste@swagger")
        String password){

}
