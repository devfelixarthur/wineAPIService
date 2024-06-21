package com.api.wiveService.WineService.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class Problem {
    private Integer status;
    private String dateTime;
    private String title;
    private List<Field> fields;

    public Problem(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.dateTime = localDateTime.format(formatter);
    }

    @AllArgsConstructor
    @Getter
    public static class Field {
        private String name;
        private String message;
    }

}
