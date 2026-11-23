package com.bootcamp.descuento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

/**
 * Tabla "discounts". Modelo de PERSISTENCIA: no se expone directo en la API.
 * La API usa los DTOs generados del contrato; la conversión vive en DiscountMapper.
 */
@Entity
@Table(name = "discounts")
public class DiscountEntity {

    @Id
    @Column(name = "product_id")
    private Long productId;

    /** Porcentaje 0-100 con 2 decimales (nunca double). */
    @Column(name = "percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal percentage;

    @Column(name = "description", nullable = false, length = 120)
    private String description;

    protected DiscountEntity() {
        // Requerido por JPA
    }

    public DiscountEntity(Long productId, BigDecimal percentage, String description) {
        this.productId = productId;
        this.percentage = percentage;
        this.description = description;
    }

    public Long getProductId() {
        return productId;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public String getDescription() {
        return description;
    }
}
