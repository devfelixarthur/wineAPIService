package com.api.wiveService.WineService.domain.wine.dto;

import com.api.wiveService.WineService.domain.user.dto.UserDTO;
import com.api.wiveService.WineService.domain.wine.bean.Wine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWineDTO {
    private Long totalWine;
    private List<Wine> wines;
}
