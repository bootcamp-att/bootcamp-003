package com.bootcamp.descuento.controller;

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

import com.bootcamp.descuento.entity.DiscountEntity;
import com.bootcamp.descuento.repository.DiscountRepository;
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
 * Verifica cada promesa de discount-api.yaml sobre la capa web (sin BD).
 * Las aserciones de mensajes de validación no dependen del idioma de la JVM.
 */
@WebMvcTest(DiscountController.class)
@DisplayName("API de Descuentos (contrato discount-api.yaml)")
class DiscountControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DiscountRepository repository;

    private static DiscountEntity promo() {
        return new DiscountEntity(1L, new BigDecimal("10.0"), "Promo septiembre");
    }

    private static DiscountEntity liquidacion() {
        return new DiscountEntity(2L, new BigDecimal("25.0"), "Liquidacion");
    }

    @Nested
    @DisplayName("GET /api/discount/{productId}")
    class Detalle {

        @Test
        @DisplayName("200: producto con descuento, porcentaje con 2 decimales")
        void conDescuento() throws Exception {
            when(repository.findById(1L)).thenReturn(Optional.of(promo()));

            mvc.perform(get("/api/discount/{id}", 1))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.productId").value(1))
                    .andExpect(jsonPath("$.percentage").value(10.00))
                    .andExpect(jsonPath("$.description").value("Promo septiembre"));
        }

        @Test
        @DisplayName("404: producto sin descuento responde application/problem+json")
        void sinDescuento() throws Exception {
            when(repository.findById(3L)).thenReturn(Optional.empty());

            mvc.perform(get("/api/discount/{id}", 3))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.title").value("Not Found"))
                    .andExpect(jsonPath("$.detail").value("No hay descuento vigente para el producto con ID 3"))
                    .andExpect(jsonPath("$.instance").value("/api/discount/3"));
        }

        @ParameterizedTest(name = "productId={0} -> 400")
        @CsvSource({"0", "-5"})
        @DisplayName("400: productId menor a 1 (minimum: 1 del contrato)")
        void productIdFueraDeRango(String productId) throws Exception {
            mvc.perform(get("/api/discount/{id}", productId))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(jsonPath("$.detail", allOf(startsWith("productId:"), containsString("1"))));

            verifyNoInteractions(repository);
        }

        @Test
        @DisplayName("400: productId que no es número")
        void productIdNoNumerico() throws Exception {
            mvc.perform(get("/api/discount/{id}", "abc"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(jsonPath("$.status").value(400));

            verifyNoInteractions(repository);
        }
    }

    @Nested
    @DisplayName("GET /api/discount (listado paginado)")
    class Listado {

        @Test
        @DisplayName("200: página con metadatos y orden por productId")
        void paginaSolicitada() throws Exception {
            when(repository.findAll(any(Pageable.class))).thenReturn(
                    new PageImpl<>(List.of(promo(), liquidacion()), PageRequest.of(0, 2), 2));

            mvc.perform(get("/api/discount").param("page", "0").param("size", "2"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.items.length()").value(2))
                    .andExpect(jsonPath("$.items[1].description").value("Liquidacion"))
                    .andExpect(jsonPath("$.totalElements").value(2))
                    .andExpect(jsonPath("$.totalPages").value(1));

            ArgumentCaptor<Pageable> solicitud = ArgumentCaptor.forClass(Pageable.class);
            verify(repository).findAll(solicitud.capture());
            assertThat(solicitud.getValue().getSort()).isEqualTo(Sort.by("productId"));
        }

        @Test
        @DisplayName("200: sin parámetros usa los valores por defecto del contrato (page=0, size=20)")
        void valoresPorDefecto() throws Exception {
            when(repository.findAll(any(Pageable.class))).thenReturn(
                    new PageImpl<>(List.of(), PageRequest.of(0, 20), 0));

            mvc.perform(get("/api/discount"))
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
            mvc.perform(get("/api/discount").param("page", page).param("size", size))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(jsonPath("$.detail", allOf(startsWith(parametro + ":"), containsString(limite))));

            verifyNoInteractions(repository);
        }
    }
}
