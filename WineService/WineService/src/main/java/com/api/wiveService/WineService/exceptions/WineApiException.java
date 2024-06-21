package com.api.wiveService.WineService.exceptions;

import jakarta.annotation.Generated;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WineApiException extends RuntimeException{

    private String title;
    private HttpStatus httpStatus;

    public WineApiException() {
    }

    public WineApiException(String message) {
        super(message);
    }

    public WineApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public WineApiException(String title, String message) {
        super(message);
        this.title = title;
    }

    public WineApiException (String title, String message, HttpStatus httpStatus) {
        super(message);
        this.title = title;
        this.httpStatus = httpStatus;
    }

}
