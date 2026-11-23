package com.bootcamp.catalogo.contrato;

import static com.atlassian.oai.validator.mockmvc.OpenApiValidationMatchers.openApi;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.report.LevelResolver;
import com.atlassian.oai.validator.report.ValidationReport;
import com.bootcamp.catalogo.controller.CatalogController;
import com.bootcamp.catalogo.entity.CatalogEntity;
import com.bootcamp.catalogo.repository.CatalogRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

/**
 * PRUEBAS DE CONTRATO: cada respuesta real del controlador se valida contra
 * catalog-api.yaml (tipos, campos requeridos, códigos de estado y Content-Type).
 * Si alguien cambia el código y la respuesta deja de cumplir el contrato, el build falla.
 *
 * Sólo se validan las RESPUESTAS: las peticiones inválidas (id=0, size=500) se envían
 * a propósito para comprobar que el error que regresa también cumple el contrato.
 */
@WebMvcTest(CatalogController.class)
@DisplayName("Las respuestas cumplen catalog-api.yaml")
class CatalogContratoTest {

    private static final OpenApiInteractionValidator CONTRATO = OpenApiInteractionValidator
            .createForSpecificationUrl(
                    CatalogContratoTest.class.getResource("/openapi/catalog-api.yaml").toString())
            .withLevelResolver(LevelResolver.create()
                    .withLevel("validation.request", ValidationReport.Level.IGNORE)
                    .build())
            .build();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CatalogRepository repository;

    @Test
    @DisplayName("200 del detalle cumple el esquema CatalogResponse")
    void detalle() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(
                new CatalogEntity(1L, "Laptop Pro 14", new BigDecimal("25000.00"), 15)));

        mvc.perform(get("/api/catalog/1")).andExpect(openApi().isValid(CONTRATO));
    }

    @Test
    @DisplayName("200 del listado cumple el esquema CatalogPage")
    void listado() throws Exception {
        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(
                List.of(new CatalogEntity(1L, "A", BigDecimal.ONE, 1)), PageRequest.of(0, 20), 1));

        mvc.perform(get("/api/catalog")).andExpect(openApi().isValid(CONTRATO));
    }

    @Test
    @DisplayName("404 cumple la respuesta NotFound (problem+json)")
    void noEncontrado() throws Exception {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        mvc.perform(get("/api/catalog/999")).andExpect(openApi().isValid(CONTRATO));
    }

    @Test
    @DisplayName("400 cumple la respuesta BadRequest (problem+json)")
    void peticionInvalida() throws Exception {
        mvc.perform(get("/api/catalog/0")).andExpect(openApi().isValid(CONTRATO));
    }
}
