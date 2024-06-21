package com.api.wiveService.WineService.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WineSucessException extends RuntimeException {

    private HttpStatus httpStatus;

    public WineSucessException(){
    }

    public WineSucessException(String message) {
        super(message);
    }

    public WineSucessException(String message, Throwable cause) {
        super(message, cause);
    }

    public WineSucessException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public WineSucessException(String title, String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
