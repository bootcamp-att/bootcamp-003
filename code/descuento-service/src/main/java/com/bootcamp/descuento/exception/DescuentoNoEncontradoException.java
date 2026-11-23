package com.bootcamp.descuento.exception;

/**
 * El producto no tiene descuento vigente. ManejadorErrores la convierte en
 * 404 con cuerpo application/problem+json, como lo define el contrato.
 */
public class DescuentoNoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Long productId;

    public DescuentoNoEncontradoException(Long productId) {
        super("No hay descuento vigente para el producto con ID " + productId);
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
