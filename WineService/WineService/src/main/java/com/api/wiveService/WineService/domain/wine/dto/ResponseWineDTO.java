package com.api.wiveService.WineService.domain.wine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWineDTO {
    private Long totalWine;
    private List<WineDto> wines;
}
