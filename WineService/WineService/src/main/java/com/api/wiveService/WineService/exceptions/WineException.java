package com.api.wiveService.WineService.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WineException extends RuntimeException {

    private String title;
    private HttpStatus httpStatus;

    public WineException() {
    }

    public WineException(String message) {
        super(message);
    }

    public WineException(String message, Throwable cause) {
        super(message, cause);
    }

    public WineException(String title, String message) {
        super(message);
        this.title = title;
    }

    public WineException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public WineException(String title, String message, HttpStatus httpStatus) {
        super(message);
        this.title = title;
        this.httpStatus = httpStatus;
    }

}
