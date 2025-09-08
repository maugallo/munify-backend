package com.maugallo.munify_backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.nio.file.AccessDeniedException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest req) {
        var problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validación fallida");
        problemDetail.setDetail("El body del request tiene cambos inválidos");
        problemDetail.setProperty("errors", ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> Map.of("field", fe.getField(), "message", fe.getDefaultMessage()))
                .toList());
        problemDetail.setProperty("path", req.getRequestURI());
        return problemDetail;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAcessDenied(AccessDeniedException ex, HttpServletRequest req) {
        var problemDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problemDetail.setTitle("Acesso denegado");
        problemDetail.setDetail("No se permitió el avance");
        problemDetail.setProperty("errors", ex.getMessage());
        problemDetail.setProperty("path", req.getRequestURI());
        return problemDetail;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest req) {
        var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Constraint violation");
        pd.setProperty("errors", ex.getConstraintViolations().stream()
                .map(v -> Map.of("param", v.getPropertyPath().toString(), "message", v.getMessage()))
                .toList());
        pd.setProperty("path", req.getRequestURI());
        return pd;
    }

    @ExceptionHandler({ IllegalArgumentException.class, IllegalStateException.class })
    public ProblemDetail handleBadRequest(RuntimeException ex, HttpServletRequest req) {
        var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Bad request");
        pd.setDetail(ex.getMessage());
        pd.setProperty("path", req.getRequestURI());
        return pd;
    }

    @ExceptionHandler(UnsupportedMediaTypeException.class)
    public ProblemDetail handleUnsupportedMediaTypeStatus(UnsupportedMediaTypeException ex, HttpServletRequest req) {
        var pd = ProblemDetail.forStatus(HttpStatus.NOT_ACCEPTABLE);
        pd.setTitle("Media type not supported");
        pd.setDetail(ex.getMessage());
        pd.setProperty("path", req.getRequestURI());
        return pd;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        var pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setTitle("Resource not found");
        pd.setDetail(ex.getMessage());
        pd.setProperty("path", req.getRequestURI());
        return pd;
    }

    @ExceptionHandler(StorageUnavailableException.class)
    public ProblemDetail handleStorageUnavailable(StorageUnavailableException ex, HttpServletRequest req) {
        var pd = ProblemDetail.forStatus(HttpStatus.BAD_GATEWAY);
        pd.setTitle("Storage error");
        pd.setDetail(ex.getMessage());
        pd.setProperty("path", req.getRequestURI());
        return pd;
    }

    @ExceptionHandler(S3Exception.class)
    public ProblemDetail handleS3(S3Exception ex, HttpServletRequest req) {
        var pd = ProblemDetail.forStatus(HttpStatus.BAD_GATEWAY);
        pd.setTitle("Storage error");
        pd.setDetail(ex.getMessage());
        pd.setProperty("path", req.getRequestURI());
        pd.setProperty("awsStatusCode", ex.statusCode());
        return pd;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneric(Exception ex, HttpServletRequest req) {
        var pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pd.setTitle("Unexpected error");
        pd.setDetail("Something went wrong");
        pd.setProperty("path", req.getRequestURI());
        return pd;
    }

}
