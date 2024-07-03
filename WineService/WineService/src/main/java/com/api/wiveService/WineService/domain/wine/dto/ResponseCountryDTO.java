package com.api.wiveService.WineService.domain.wine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCountryDTO {
    private Long totalCountries;
    private List<CountryDTO> countries;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CountryDTO {
        private String sigla;
        private String nome;
    }
}
