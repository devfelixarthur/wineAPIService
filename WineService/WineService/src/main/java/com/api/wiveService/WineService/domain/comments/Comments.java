package com.api.wiveService.WineService.domain.comments;


import com.api.wiveService.WineService.domain.user.User;
import com.api.wiveService.WineService.domain.wine.Wine;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Table(name="cadastro_comentarios")
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O ID do vinho é obrigatório.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vinho", nullable = false)
    private Wine wine;

    @NotNull(message = "O ID do usuário é obrigatório.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private User user;

    @NotNull(message = "O nome do usuário é obrigatório.")
    @Column(name = "nome_usuario")
    private String nomeUsuario;

    @NotNull(message = "A descrição é obrigatória.")
    @Size(min = 1, max = 500, message = "A descrição deve ter entre 1 e 500 caracteres.")
    @Column(nullable = false, length = 500)
    private String descricao;

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
