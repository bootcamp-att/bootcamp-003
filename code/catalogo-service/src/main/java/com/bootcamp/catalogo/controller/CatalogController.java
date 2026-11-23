package com.bootcamp.catalogo.controller;

import com.bootcamp.catalogo.api.CatalogApi;
import com.bootcamp.catalogo.exception.ProductoNoEncontradoException;
import com.bootcamp.catalogo.mapper.CatalogMapper;
import com.bootcamp.catalogo.model.CatalogPage;
import com.bootcamp.catalogo.model.CatalogResponse;
import com.bootcamp.catalogo.repository.CatalogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementa el contrato catalog-api.yaml (interfaz generada CatalogApi).
 *
 * - Rutas, parámetros y validaciones (@Min, @Max) vienen de la interfaz: NO se repiten aquí.
 * - Si el contrato agrega una operación, este archivo deja de compilar hasta implementarla.
 */
@RestController
public class CatalogController implements CatalogApi {

    private static final Logger log = LoggerFactory.getLogger(CatalogController.class);

    private final CatalogRepository repository;

    public CatalogController(CatalogRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<CatalogResponse> getCatalogByProductId(Long productId) {
        log.info("Consultando catálogo para el producto ID: {}", productId);

        CatalogResponse respuesta = repository.findById(productId)
                .map(CatalogMapper::toResponse)
                .orElseThrow(() -> new ProductoNoEncontradoException(productId));

        log.info("Producto encontrado: {} (stock: {})", respuesta.getName(), respuesta.getStock());
        return ResponseEntity.ok(respuesta);
    }

    @Override
    public ResponseEntity<CatalogPage> listCatalog(Integer page, Integer size) {
        log.info("Listando catálogo: página {}, tamaño {}", page, size);

        // Orden fijo: sin él, la paginación puede repetir u omitir productos entre páginas
        PageRequest pagina = PageRequest.of(page, size, Sort.by("productId"));
        return ResponseEntity.ok(CatalogMapper.toPage(repository.findAll(pagina)));
    }
}
