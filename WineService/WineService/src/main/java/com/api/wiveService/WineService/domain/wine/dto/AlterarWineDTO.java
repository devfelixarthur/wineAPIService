package com.api.wiveService.WineService.domain.wine.dto;

import com.api.wiveService.WineService.util.ValidBase64;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AlterarWineDTO (

        @NotNull(message = "O Id é um campo obrigatório.")
        Long id,

        @Size(min=3, message = "O campo nome deve ter no mínimo 3 caracteres.")
        String nome,

        @Size(min=3, message = "O campo descricao deve ter no mínimo 3 caracteres.")
        String descricao,


        @Size(min=3, message = "O campo país deve ter no mínimo 3 caracteres.")
        String pais,


        @Size(min=3, message = "O campo adega deve ter no mínimo 3 caracteres.")
        String adega,

        @Size(min=3, message = "O campo uva deve ter no mínimo 3 caracteres.")
        String uva,

        @Min(value = 1501, message = "O ano deve ser superior a 1500.")
        @Max(value = 9999, message = "O ano deve ser inferior a 9999.")
        Integer safra,

        @ValidBase64
        String imagem
) {
}
