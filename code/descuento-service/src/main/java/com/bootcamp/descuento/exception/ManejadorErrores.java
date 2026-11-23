package com.bootcamp.descuento.exception;

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
 * Al heredar de ResponseEntityExceptionHandler, los errores estándar de Spring
 * (tipo inválido, parámetros faltantes) también salen como problem+json.
 */
@RestControllerAdvice
public class ManejadorErrores extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ManejadorErrores.class);

    /** 404: el producto no tiene descuento (para productos-service equivale a 0%). */
    @ExceptionHandler(DescuentoNoEncontradoException.class)
    public ProblemDetail descuentoNoEncontrado(DescuentoNoEncontradoException ex) {
        // INFO y no WARN: no tener descuento es un caso normal del negocio
        log.info("Sin descuento vigente para el producto: {}", ex.getProductId());
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problema.setTitle("Not Found");
        return problema;
    }

    /** 400: viola una regla del contrato (@Min, @Max generados desde minimum/maximum). */
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

    /** "getDiscountByProductId.productId" -> "productId: <mensaje>" */
    private static String describir(ConstraintViolation<?> violacion) {
        String ruta = violacion.getPropertyPath().toString();
        String parametro = ruta.substring(ruta.lastIndexOf('.') + 1);
        return parametro + ": " + violacion.getMessage();
    }
}
