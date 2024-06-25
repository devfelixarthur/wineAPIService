package com.api.wiveService.WineService.domain.wine.dto;

import com.api.wiveService.WineService.domain.comments.bean.Comments;
import com.api.wiveService.WineService.domain.comments.dto.CommentsDTO;
import com.api.wiveService.WineService.domain.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WineDto {
    private Long id;
    private String nome;
    private String pais;
    private String adega;
    private Integer safra;
    private String imagem;
    private String status;
    private LocalDateTime dataCadastro;
    List<CommentsDTO> comments;
}
