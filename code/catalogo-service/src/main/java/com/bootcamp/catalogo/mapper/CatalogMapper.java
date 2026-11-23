package com.bootcamp.catalogo.mapper;

import com.bootcamp.catalogo.entity.CatalogEntity;
import com.bootcamp.catalogo.model.CatalogPage;
import com.bootcamp.catalogo.model.CatalogResponse;
import java.math.RoundingMode;
import org.springframework.data.domain.Page;

/**
 * Traduce del modelo de persistencia (entidad JPA) al modelo del contrato (DTOs generados).
 * Si el contrato cambia, el compilador marca aquí qué hay que ajustar.
 */
public final class CatalogMapper {

    private CatalogMapper() {
        // Clase utilitaria: no se instancia
    }

    public static CatalogResponse toResponse(CatalogEntity entity) {
        return new CatalogResponse()
                .productId(entity.getProductId())
                .name(entity.getName())
                // El contrato promete 2 decimales
                .basePrice(entity.getBasePrice().setScale(2, RoundingMode.HALF_UP))
                .stock(entity.getStock());
    }

    public static CatalogPage toPage(Page<CatalogEntity> page) {
        return new CatalogPage()
                .items(page.getContent().stream().map(CatalogMapper::toResponse).toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages());
    }
}
