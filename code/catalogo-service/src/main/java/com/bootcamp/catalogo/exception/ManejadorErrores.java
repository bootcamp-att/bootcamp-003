package com.bootcamp.catalogo.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Convierte excepciones en respuestas RFC 9457 (application/problem+json),
 * tal como las define el contrato en components/responses (BadRequest, NotFound).
 *
 * Al heredar de ResponseEntityExceptionHandler, Spring ya resuelve como 400 los
 * errores estándar: tipo inválido (/api/catalog/abc), parámetros faltantes, etc.
 */
@RestControllerAdvice
public class ManejadorErrores extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ManejadorErrores.class);

    /** 404: el recurso no existe. */
    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ProblemDetail productoNoEncontrado(ProductoNoEncontradoException ex) {
        log.warn("Producto no encontrado en catálogo: {}", ex.getProductId());
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problema.setTitle("Not Found");
        return problema;
    }

    /**
     * 400: viola una regla del contrato (@Min, @Max generados desde minimum/maximum).
     * La interfaz generada tiene @Validated, por eso llega como ConstraintViolationException.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail parametroInvalido(ConstraintViolationException ex) {
        String detalle = ex.getConstraintViolations().stream()
                .map(ManejadorErrores::describir)
                .sorted()
                .collect(Collectors.joining("; "));
        log.warn("Petición rechazada por el contrato: {}", detalle);
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detalle);
        problema.setTitle("Bad Request");
        return problema;
    }

    /** "getCatalogByProductId.productId" -> "productId: debe ser mayor que o igual a 1" */
    private static String describir(ConstraintViolation<?> violacion) {
        String ruta = violacion.getPropertyPath().toString();
        String parametro = ruta.substring(ruta.lastIndexOf('.') + 1);
        return parametro + ": " + violacion.getMessage();
    }
}
