package com.bootcamp.catalogo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bootcamp.catalogo.entity.CatalogEntity;
import com.bootcamp.catalogo.repository.CatalogRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Verifica cada promesa de catalog-api.yaml sobre la capa web.
 * - @WebMvcTest levanta sólo controlador, validación y ManejadorErrores (sin BD).
 * - El repositorio es un mock: controlamos exactamente qué "hay" en la base.
 * Son las mismas 6 pruebas que hiciste con curl, ahora automáticas en cada build.
 */
@WebMvcTest(CatalogController.class)
@DisplayName("API de Catálogo (contrato catalog-api.yaml)")
class CatalogControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CatalogRepository repository;

    private static CatalogEntity laptop() {
        return new CatalogEntity(1L, "Laptop Pro 14", new BigDecimal("25000.00"), 15);
    }

    private static CatalogEntity mouse() {
        return new CatalogEntity(2L, "Mouse Inalambrico", new BigDecimal("450.00"), 120);
    }

    @Nested
    @DisplayName("GET /api/catalog/{productId}")
    class Detalle {

        @Test
        @DisplayName("200: producto existente con todos los campos requeridos")
        void productoExistente() throws Exception {
            when(repository.findById(1L)).thenReturn(Optional.of(laptop()));

            mvc.perform(get("/api/catalog/{id}", 1))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.productId").value(1))
                    .andExpect(jsonPath("$.name").value("Laptop Pro 14"))
                    .andExpect(jsonPath("$.basePrice").value(25000.00))
                    .andExpect(jsonPath("$.stock").value(15));
        }

        @Test
        @DisplayName("404: producto inexistente responde application/problem+json")
        void productoInexistente() throws Exception {
            when(repository.findById(999L)).thenReturn(Optional.empty());

            mvc.perform(get("/api/catalog/{id}", 999))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.title").value("Not Found"))
                    .andExpect(jsonPath("$.detail").value("No existe el producto con ID 999"))
                    .andExpect(jsonPath("$.instance").value("/api/catalog/999"));
        }

        @ParameterizedTest(name = "productId={0} -> 400")
        @CsvSource({"0", "-5"})
        @DisplayName("400: productId menor a 1 (minimum: 1 del contrato)")
        void productIdFueraDeRango(String productId) throws Exception {
            mvc.perform(get("/api/catalog/{id}", productId))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                    // El texto lo traduce Hibernate Validator según el idioma de la JVM
                    // ("debe ser mayor..." / "must be greater..."): se verifica lo que NO cambia.
                    .andExpect(jsonPath("$.detail", allOf(startsWith("productId:"), containsString("1"))));

            // La validación ocurre ANTES de tocar la base de datos
            verifyNoInteractions(repository);
        }

        @Test
        @DisplayName("400: productId que no es número")
        void productIdNoNumerico() throws Exception {
            mvc.perform(get("/api/catalog/{id}", "abc"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(jsonPath("$.status").value(400));

            verifyNoInteractions(repository);
        }
    }

    @Nested
    @DisplayName("GET /api/catalog (listado paginado)")
    class Listado {

        @Test
        @DisplayName("200: página con metadatos y orden por productId")
        void paginaSolicitada() throws Exception {
            when(repository.findAll(any(Pageable.class))).thenReturn(
                    new PageImpl<>(List.of(laptop(), mouse()), PageRequest.of(0, 2), 3));

            mvc.perform(get("/api/catalog").param("page", "0").param("size", "2"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.items.length()").value(2))
                    .andExpect(jsonPath("$.items[0].name").value("Laptop Pro 14"))
                    .andExpect(jsonPath("$.page").value(0))
                    .andExpect(jsonPath("$.size").value(2))
                    .andExpect(jsonPath("$.totalElements").value(3))
                    .andExpect(jsonPath("$.totalPages").value(2));

            ArgumentCaptor<Pageable> solicitud = ArgumentCaptor.forClass(Pageable.class);
            verify(repository).findAll(solicitud.capture());
            assertThat(solicitud.getValue().getSort()).isEqualTo(Sort.by("productId"));
        }

        @Test
        @DisplayName("200: sin parámetros usa los valores por defecto del contrato (page=0, size=20)")
        void valoresPorDefecto() throws Exception {
            when(repository.findAll(any(Pageable.class))).thenReturn(
                    new PageImpl<>(List.of(), PageRequest.of(0, 20), 0));

            mvc.perform(get("/api/catalog"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.items.length()").value(0));

            ArgumentCaptor<Pageable> solicitud = ArgumentCaptor.forClass(Pageable.class);
            verify(repository).findAll(solicitud.capture());
            assertThat(solicitud.getValue().getPageNumber()).isZero();
            assertThat(solicitud.getValue().getPageSize()).isEqualTo(20);
        }

        @ParameterizedTest(name = "page={0}, size={1} -> 400 en ''{2}'' (límite {3})")
        @CsvSource({
            "-1, 20,  page, 0",
            "0,  0,   size, 1",
            "0,  101, size, 100"
        })
        @DisplayName("400: paginación fuera de los límites del contrato")
        void paginacionInvalida(String page, String size, String parametro, String limite) throws Exception {
            mvc.perform(get("/api/catalog").param("page", page).param("size", size))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                    // Independiente del idioma: nombra el parámetro y el límite del contrato
                    .andExpect(jsonPath("$.detail", allOf(startsWith(parametro + ":"), containsString(limite))));

            verifyNoInteractions(repository);
        }
    }
}
