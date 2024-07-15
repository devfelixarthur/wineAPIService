package com.api.wiveService.WineService.domain.wine.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUvasDTO {
    private Long totaluvas;
    private List<String> uvas;
}
