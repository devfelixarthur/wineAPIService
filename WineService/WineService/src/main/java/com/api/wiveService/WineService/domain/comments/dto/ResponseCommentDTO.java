package com.api.wiveService.WineService.domain.comments.dto;

import com.api.wiveService.WineService.domain.wine.dto.WineDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCommentDTO {
    private Long totalComments;
    private List<CommentsDTO> comments;
}
