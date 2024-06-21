package com.api.wiveService.WineService.domain.user;

import jakarta.validation.constraints.*;


public record RegisterUserDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres")
        String senha,

        @NotBlank(message = "O Campo dtNascimento é obrigatório")
        String dtNascimento
) {
}
