package com.maugallo.munify_backend.exception;

import lombok.NonNull;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.*;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    // VALIDACIÓN (@Valid @RequestBody)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        ProblemDetail pd = createProblemDetail(
                ex, status, "La solicitud contiene campos inválidos.",
                null, null, request
        );
        pd.setTitle("Solicitud inválida");

        BindingResult bindingResult = ex.getBindingResult();
        List<Map<String, Object>> errors = bindingResult.getFieldErrors().stream()
                .map(err -> Map.of(
                        "campo", err.getField(),
                        "valorRechazado", err.getRejectedValue(),
                        "mensaje", messageOf(err),
                        "codigo", err.getCode()))
                .collect(Collectors.toList());
        pd.setProperty("errores", errors);

        return handleExceptionInternal(ex, pd, headers, status, request);
    }

    private static String messageOf(FieldError err) {
        return StringUtils.hasText(err.getDefaultMessage())
                ? err.getDefaultMessage()
                : "Valor inválido";
    }

    // VALIDACIÓN (@RequestParam/@PathVariable)
    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(
            @NonNull HandlerMethodValidationException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        ProblemDetail pd = createProblemDetail(
                ex, status, "Parámetros de la solicitud inválidos.", null, null, request);
        pd.setTitle("Solicitud inválida");

        List<Map<String, Object>> errors = ex.getParameterValidationResults().stream()
                .flatMap(paramResult -> paramResult.getResolvableErrors().stream()
                        .map(res -> Map.<String, Object>of(
                                "parametro", Optional.ofNullable(paramResult.getMethodParameter().getParameterName()).orElse("argumento"),
                                "mensaje", StringUtils.hasText(res.getDefaultMessage()) ? res.getDefaultMessage() : "Valor inválido",
                                "codigos", Arrays.asList(res.getCodes() == null ? new String[0] : res.getCodes())
                        )))
                .collect(Collectors.toList());
        pd.setProperty("errores", errors);

        return handleExceptionInternal(ex, pd, headers, status, request);
    }

    // BODY
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NonNull HttpMessageNotReadableException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        ProblemDetail pd = createProblemDetail(
                ex, status, "No se pudo leer el cuerpo de la solicitud (JSON mal formado).", null, null, request);
        pd.setTitle("Solicitud inválida");
        return handleExceptionInternal(ex, pd, headers, status, request);
    }

    // TYPE MISMATCH (query/path param)
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            @NonNull TypeMismatchException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        ProblemDetail pd = createProblemDetail(
                ex, status, "El tipo de dato del parámetro es inválido.", null, null, request);
        pd.setTitle("Solicitud inválida");

        if (ex instanceof MethodArgumentTypeMismatchException matme) {
            pd.setDetail(("El parámetro '%s' debe ser de tipo %s.")
                    .formatted(matme.getName(),
                            matme.getRequiredType() != null ? matme.getRequiredType().getSimpleName() : "requerido"));
            pd.setProperty("parametro", matme.getName());
            pd.setProperty("tipoRequerido", matme.getRequiredType() != null ? matme.getRequiredType().getName() : null);
            pd.setProperty("valor", matme.getValue());
        }
        return handleExceptionInternal(ex, pd, headers, status, request);
    }

    // MISSING PARAM
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            @NonNull MissingServletRequestParameterException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        ProblemDetail pd = createProblemDetail(
                ex, status, "Falta un parámetro obligatorio en la solicitud.", null, null, request);
        pd.setTitle("Solicitud inválida");
        pd.setDetail(("Falta el parámetro '%s'.").formatted(ex.getParameterName()));
        return handleExceptionInternal(ex, pd, headers, status, request);
    }

    // 404 dinámico (no hay handler)
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            @NonNull NoHandlerFoundException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        ProblemDetail pd = createProblemDetail(
                ex, status, "No se encontró el recurso solicitado.", null, null, request);
        pd.setTitle("No encontrado");
        pd.setDetail(("No se encontró un handler para %s %s.")
                .formatted(ex.getHttpMethod(), ex.getRequestURL()));
        return handleExceptionInternal(ex, pd, headers, status, request);
    }

    // 404 estático (recursos)
    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(
            @NonNull NoResourceFoundException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        ProblemDetail pd = createProblemDetail(
                ex, status, "Recurso no encontrado.", null, null, request);
        pd.setTitle("No encontrado");
        return handleExceptionInternal(ex, pd, headers, status, request);
    }

    // 405 / 415
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            @NonNull  HttpRequestMethodNotSupportedException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        ProblemDetail pd = createProblemDetail(
                ex, status, "Método HTTP no permitido para este recurso.", null, null, request);
        pd.setTitle("Método no permitido");
        pd.setProperty("metodosSoportados", ex.getSupportedHttpMethods());
        return handleExceptionInternal(ex, pd, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            @NonNull HttpMediaTypeNotSupportedException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        ProblemDetail pd = createProblemDetail(
                ex, status, "Tipo de contenido no soportado.", null, null, request);
        pd.setTitle("Tipo de contenido no soportado");
        pd.setProperty("soportados", ex.getSupportedMediaTypes());
        return handleExceptionInternal(ex, pd, headers, status, request);
    }

    // NEGOCIO
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFound(ResourceNotFoundException ex) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setTitle("Recurso no encontrado");
        pd.setDetail(ex.getMessage());
        return pd;
    }

    @ExceptionHandler(StorageUnavailableException.class)
    public ResponseEntity<ProblemDetail> handleStorageUnavailable(StorageUnavailableException ex) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.SERVICE_UNAVAILABLE);
        pd.setTitle("Servicio no disponible");
        pd.setDetail(ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                // .header(HttpHeaders.RETRY_AFTER, "30") // opcional si querés indicar reintento
                .body(pd);
    }

    @ExceptionHandler(UnsupportedMediaTypeStatusException.class)
    public ResponseEntity<ProblemDetail> handleUnsupportedMediaType(UnsupportedMediaTypeStatusException ex) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        pd.setTitle("Tipo de contenido no soportado");
        pd.setDetail(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(pd);
    }

    // FALLBACK (cualquier otra Exception)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAny(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemDetail pd = createProblemDetail(
                ex, status,
                "Ocurrió un error inesperado.",
                null, null, request
        );
        pd.setTitle("Error interno del servidor");
        return handleExceptionInternal(ex, pd, new HttpHeaders(), status, request);
    }

}
