package com.bootcamp.catalogo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

/**
 * Tabla "catalog". Es el modelo de PERSISTENCIA: no se expone directo en la API.
 * La API usa los DTOs generados del contrato (CatalogResponse, CatalogPage);
 * la conversión entre ambos vive en CatalogMapper.
 */
@Entity
@Table(name = "catalog")
public class CatalogEntity {

    @Id
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name", nullable = false, length = 120)
    private String name;

    /** Dinero: BigDecimal con 2 decimales (nunca double). */
    @Column(name = "base_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal basePrice;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    protected CatalogEntity() {
        // Requerido por JPA
    }

    public CatalogEntity(Long productId, String name, BigDecimal basePrice, Integer stock) {
        this.productId = productId;
        this.name = name;
        this.basePrice = basePrice;
        this.stock = stock;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public Integer getStock() {
        return stock;
    }
}
