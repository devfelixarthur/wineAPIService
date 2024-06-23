package com.api.wiveService.WineService.domain.wine.dto;

import com.api.wiveService.WineService.util.ValidBase64;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Max;

public record CadastrarWineDTO(

        @NotNull(message = "O campo nome é obrigatório.")
        @Size(min=3, message = "O campo nome deve ter no mínimo 3 caracteres.")
        String nome,


        @NotNull(message = "O campo país é obrigatório.")
        @Size(min=3, message = "O campo país deve ter no mínimo 3 caracteres.")
        String pais,


        @NotNull(message = "O campo adega é obrigatório.")
        @Size(min=3, message = "O campo adega deve ter no mínimo 3 caracteres.")
        String adega,

        @NotNull(message = "O campo uva é obrigatório.")
        @Size(min=3, message = "O campo uva deve ter no mínimo 3 caracteres.")
        String uva,

        @NotNull(message = "O campo safra é obrigatório.")
        @Min(value = 1501, message = "O ano deve ser superior a 1500.")
        @Max(value = 9999, message = "O ano deve ser inferior a 9999.")
        Integer safra,

        @NotNull(message = "O campo imagem é obrigatório.")
        @ValidBase64
        String imagem

) {
}
