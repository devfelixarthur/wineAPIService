package com.api.wiveService.WineService.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ErrorMessage {
    private Integer statusCode;
    private Instant timestamp;
    private String error;
    private String description;

    public ErrorMessage(LocalDateTime now) {
    }

    public ErrorMessage(Integer statusCode, Instant timestamp, String error, String description) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.error = error;
        this.description = description;
    }


}
