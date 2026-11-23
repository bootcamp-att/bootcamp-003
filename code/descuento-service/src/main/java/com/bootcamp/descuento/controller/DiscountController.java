package com.bootcamp.descuento.controller;

import com.bootcamp.descuento.api.DiscountApi;
import com.bootcamp.descuento.exception.DescuentoNoEncontradoException;
import com.bootcamp.descuento.mapper.DiscountMapper;
import com.bootcamp.descuento.model.DiscountPage;
import com.bootcamp.descuento.model.DiscountResponse;
import com.bootcamp.descuento.repository.DiscountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementa el contrato discount-api.yaml (interfaz generada DiscountApi).
 * Rutas, parámetros y validaciones vienen de la interfaz: NO se repiten aquí.
 */
@RestController
public class DiscountController implements DiscountApi {

    private static final Logger log = LoggerFactory.getLogger(DiscountController.class);

    private final DiscountRepository repository;

    public DiscountController(DiscountRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<DiscountResponse> getDiscountByProductId(Long productId) {
        log.info("Buscando descuento para el producto ID: {}", productId);

        DiscountResponse respuesta = repository.findById(productId)
                .map(DiscountMapper::toResponse)
                .orElseThrow(() -> new DescuentoNoEncontradoException(productId));

        log.info("Descuento encontrado para producto ID {}: {}% - {}",
                productId, respuesta.getPercentage(), respuesta.getDescription());
        return ResponseEntity.ok(respuesta);
    }

    @Override
    public ResponseEntity<DiscountPage> listDiscounts(Integer page, Integer size) {
        log.info("Listando descuentos: página {}, tamaño {}", page, size);

        // Orden fijo: sin él, la paginación puede repetir u omitir elementos entre páginas
        PageRequest pagina = PageRequest.of(page, size, Sort.by("productId"));
        return ResponseEntity.ok(DiscountMapper.toPage(repository.findAll(pagina)));
    }
}
