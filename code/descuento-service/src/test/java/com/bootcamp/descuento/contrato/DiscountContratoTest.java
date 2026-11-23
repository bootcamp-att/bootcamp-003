package com.bootcamp.descuento.contrato;

import static com.atlassian.oai.validator.mockmvc.OpenApiValidationMatchers.openApi;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.report.LevelResolver;
import com.atlassian.oai.validator.report.ValidationReport;
import com.bootcamp.descuento.controller.DiscountController;
import com.bootcamp.descuento.entity.DiscountEntity;
import com.bootcamp.descuento.repository.DiscountRepository;
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
 * PRUEBAS DE CONTRATO: cada respuesta real del controlador se valida contra discount-api.yaml.
 * Sólo se validan RESPUESTAS: las peticiones inválidas se envían a propósito.
 */
@WebMvcTest(DiscountController.class)
@DisplayName("Las respuestas cumplen discount-api.yaml")
class DiscountContratoTest {

    private static final OpenApiInteractionValidator CONTRATO = OpenApiInteractionValidator
            .createForSpecificationUrl(
                    DiscountContratoTest.class.getResource("/openapi/discount-api.yaml").toString())
            .withLevelResolver(LevelResolver.create()
                    .withLevel("validation.request", ValidationReport.Level.IGNORE)
                    .build())
            .build();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DiscountRepository repository;

    @Test
    @DisplayName("200 del detalle cumple el esquema DiscountResponse")
    void detalle() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(
                new DiscountEntity(1L, new BigDecimal("10.0"), "Promo septiembre")));

        mvc.perform(get("/api/discount/1")).andExpect(openApi().isValid(CONTRATO));
    }

    @Test
    @DisplayName("200 del listado cumple el esquema DiscountPage")
    void listado() throws Exception {
        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(
                List.of(new DiscountEntity(1L, BigDecimal.TEN, "A")), PageRequest.of(0, 20), 1));

        mvc.perform(get("/api/discount")).andExpect(openApi().isValid(CONTRATO));
    }

    @Test
    @DisplayName("404 cumple la respuesta NotFound (problem+json)")
    void sinDescuento() throws Exception {
        when(repository.findById(3L)).thenReturn(Optional.empty());

        mvc.perform(get("/api/discount/3")).andExpect(openApi().isValid(CONTRATO));
    }

    @Test
    @DisplayName("400 cumple la respuesta BadRequest (problem+json)")
    void peticionInvalida() throws Exception {
        mvc.perform(get("/api/discount/0")).andExpect(openApi().isValid(CONTRATO));
    }
}
