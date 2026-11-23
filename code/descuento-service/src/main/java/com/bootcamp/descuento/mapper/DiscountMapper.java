package com.bootcamp.descuento.mapper;

import com.bootcamp.descuento.entity.DiscountEntity;
import com.bootcamp.descuento.model.DiscountPage;
import com.bootcamp.descuento.model.DiscountResponse;
import java.math.RoundingMode;
import org.springframework.data.domain.Page;

/**
 * Traduce del modelo de persistencia (entidad JPA) al modelo del contrato (DTOs generados).
 * Si el contrato cambia, el compilador marca aquí qué hay que ajustar.
 */
public final class DiscountMapper {

    private DiscountMapper() {
        // Clase utilitaria: no se instancia
    }

    public static DiscountResponse toResponse(DiscountEntity entity) {
        return new DiscountResponse()
                .productId(entity.getProductId())
                // El contrato promete 2 decimales
                .percentage(entity.getPercentage().setScale(2, RoundingMode.HALF_UP))
                .description(entity.getDescription());
    }

    public static DiscountPage toPage(Page<DiscountEntity> page) {
        return new DiscountPage()
                .items(page.getContent().stream().map(DiscountMapper::toResponse).toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages());
    }
}
