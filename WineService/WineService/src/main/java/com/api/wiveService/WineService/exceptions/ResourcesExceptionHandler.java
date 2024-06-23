package com.api.wiveService.WineService.exceptions;

import com.api.wiveService.WineService.util.ResponsePadraoDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public void handleAuthenticationException(AuthenticationException authException, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponsePadraoDTO erro = ResponsePadraoDTO.falha("Token inexistente, inválido ou expirado");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(erro.toJson());
    }

    public void handleAccessDeniedException(AccessDeniedException accessDeniedException, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponsePadraoDTO erro = ResponsePadraoDTO.falha("Acesso negado: você não tem permissão para acessar este recurso.");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write(erro.toJson());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponsePadraoDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String mensagem = "O parâmetro '" + ex.getName() + "' deve ser do tipo " + ex.getRequiredType().getSimpleName();
        ResponsePadraoDTO erro = ResponsePadraoDTO.falha(mensagem);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponsePadraoDTO> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        List<Problem.Field> fields = new ArrayList<>();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();

        for (ConstraintViolation<?> violation : violations) {
            String name = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            fields.add(new Problem.Field(name, message));
        }

        Problem problem = new Problem(LocalDateTime.now());
        problem.setStatus(HttpStatus.BAD_REQUEST.value());
        problem.setTitle("Um ou mais campos inválidos, preencha corretamente!");
        problem.setFields(fields);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponsePadraoDTO.falha(problem.getTitle()));
    }
}
