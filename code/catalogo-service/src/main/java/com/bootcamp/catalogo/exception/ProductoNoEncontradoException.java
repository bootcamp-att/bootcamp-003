package com.bootcamp.catalogo.exception;

/**
 * El producto solicitado no existe. ManejadorErrores la convierte en
 * 404 con cuerpo application/problem+json, como lo define el contrato.
 */
public class ProductoNoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Long productId;

    public ProductoNoEncontradoException(Long productId) {
        super("No existe el producto con ID " + productId);
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
