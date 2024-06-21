package com.api.wiveService.WineService.exceptions;

import com.api.wiveService.WineService.util.ResponsePadraoDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ResourcesExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<Problem.Field> fields = new ArrayList<>();
        for (var error : ex.getBindingResult().getAllErrors()) {
            String name = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            fields.add(new Problem.Field(name, message));
        }

        Problem problem = new Problem(LocalDateTime.now());
        problem.setStatus(status.value());
        problem.setTitle("Um ou mais campos inválidos, preencha corretamente!");
        problem.setFields(fields);

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(WineException.class)
    public ResponseEntity<ResponsePadraoDTO> WineException(WineException e, HttpServletRequest httpServletRequest) {
        ResponsePadraoDTO erro = ResponsePadraoDTO.falha(e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(erro);
    }

    @ExceptionHandler(WineApiException.class)
    public ResponseEntity<ErrorMessage> wineApiException(WineApiException e, HttpServletRequest httpServletRequest) {
        ErrorMessage errorMessage = new ErrorMessage(e.getHttpStatus().value(), Instant.now(), e.getTitle(), e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(errorMessage);
    }


    @ExceptionHandler(WineSucessException.class)
    public ResponseEntity<ResponsePadraoDTO> WineSucessException(WineSucessException e, HttpServletRequest httpServletRequest) {
        ResponsePadraoDTO erro = ResponsePadraoDTO.sucesso(e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(erro);
    }
}
