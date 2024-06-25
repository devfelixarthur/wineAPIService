package com.api.wiveService.WineService.domain.comments.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditCommentDTO {

    @NotNull(message = "O idComment é um campo obrigatório.")
    @Min(value = 1, message = "O idComment deve ser maior que 0.")
    Long idComment;

    @NotBlank(message = "O campo message é um campo obrigatório e não pode estar em branco.")
    @Size(min = 3, message = "O campo message deve ter pelo menos 3 caracteres.")
    String message;
}
