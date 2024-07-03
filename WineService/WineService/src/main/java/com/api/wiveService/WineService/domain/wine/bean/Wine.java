package com.api.wiveService.WineService.domain.wine.bean;

import com.api.wiveService.WineService.domain.wine.dto.Pais;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Table(name = "cadastro_wine")
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class Wine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O nome do vinho é obrigatório.")
    @Size(min = 1, max = 255, message = "O nome do vinho deve ter entre 1 e 255 caracteres.")
    @Column(length = 255)
    private String nome;

    @NotNull(message = "O país é obrigatório.")
    @Size(min = 1, max = 100, message = "O país deve ter entre 1 e 100 caracteres.")
    @Column(length = 100)
    private String pais;

    @NotNull(message = "A adega é obrigatória.")
    @Size(min = 1, max = 255, message = "A adega deve ter entre 1 e 255 caracteres.")
    @Column(length = 255)
    private String adega;

    @NotNull(message = "A safra é obrigatória.")
    @Min(value = 1501, message = "O ano deve ser superior a 1500.")
    @Max(value = 9999, message = "O ano deve ser inferior a 9999.")
    @Column(nullable = false)
    private Integer safra;

    @Size(max = 500000, message = "A imagem deve ter no máximo 500KB.")
    private String imagem;

    @NotNull(message = "A uva é obrigatória.")
    @Size(min = 1, max = 100, message = "A uva deve ter entre 1 e 100 caracteres.")
    @Column(length = 100)
    private String uva;

    @NotNull(message = "O status é obrigatório.")
    @Size(min = 1, max = 50, message = "O status deve ter entre 1 e 50 caracteres.")
    @Column(length = 50)
    private String status;

    @NotNull(message = "A data de cadastro é obrigatória.")
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @NotNull(message = "A data de atualização é obrigatória.")
    @Column(name = "data_update")
    private LocalDateTime dataUpdate;

}
