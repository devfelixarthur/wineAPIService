package com.api.wiveService.WineService.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRecoveryDTO {
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;

    @NotBlank(message = "Data de nascimento é obrigatória")
    @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "Data de nascimento deve estar no formato dd/MM/yyyy")
    private String dtNascimento;

    @NotBlank(message = "Senha é obrigatória")
    private String senha;

    @NotBlank(message = "Confirmação de senha é obrigatória")
    private String confirmaSenha;
}
