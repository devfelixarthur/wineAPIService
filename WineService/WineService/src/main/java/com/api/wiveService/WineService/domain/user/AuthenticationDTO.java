package com.api.wiveService.WineService.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO (

        @Email
        String email,

        @NotBlank (message = "O campo password é obrigatório e não pode ser null")
        String password){

}
