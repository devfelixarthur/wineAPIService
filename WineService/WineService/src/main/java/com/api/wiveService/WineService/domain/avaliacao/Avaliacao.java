package com.api.wiveService.WineService.domain.avaliacao;

import com.api.wiveService.WineService.domain.user.User;
import com.api.wiveService.WineService.domain.wine.Wine;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Table(name="cadastro_avaliacao")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O ID do usuário é obrigatório.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private User user;

    @NotNull(message = "O ID do vinho é obrigatório.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vinho", nullable = false)
    private Wine wine;

    @NotNull(message = "A avaliação é obrigatória.")
    @Min(value = 1, message = "A avaliação deve ser no mínimo 1.")
    @Max(value = 5, message = "A avaliação deve ser no máximo 5.")
    @Column(nullable = false)
    private Integer avaliacao;

    @NotNull(message = "O status é obrigatório.")
    @Size(min = 1, max = 50, message = "O status deve ter entre 1 e 50 caracteres.")
    @Column(nullable = false, length = 50)
    private String status;

    @NotNull(message = "A data de cadastro é obrigatória.")
    @Column(name = "data_cadastro")
    private Date dataCadastro;

    @NotNull(message = "A data de atualização é obrigatória.")
    @Column(name = "data_update")
    private Date dataUpdate;

}
