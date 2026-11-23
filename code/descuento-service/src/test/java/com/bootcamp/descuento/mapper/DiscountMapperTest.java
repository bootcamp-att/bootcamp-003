package com.bootcamp.descuento.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.bootcamp.descuento.entity.DiscountEntity;
import com.bootcamp.descuento.model.DiscountPage;
import com.bootcamp.descuento.model.DiscountResponse;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/** Prueba unitaria pura: sin Spring. */
@DisplayName("DiscountMapper: entidad -> DTOs del contrato")
class DiscountMapperTest {

    @Test
    @DisplayName("Copia todos los campos de la entidad al DTO")
    void mapeaTodosLosCampos() {
        DiscountResponse dto = DiscountMapper.toResponse(
                new DiscountEntity(1L, new BigDecimal("10.0"), "Promo septiembre"));

        assertThat(dto.getProductId()).isEqualTo(1L);
        assertThat(dto.getPercentage()).isEqualByComparingTo("10.00");
        assertThat(dto.getDescription()).isEqualTo("Promo septiembre");
    }

    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({
        "10,      10.00",
        "25.5,    25.50",
        "12.344,  12.34",
        "12.345,  12.35",    // HALF_UP
        "100,     100.00"
    })
    @DisplayName("El porcentaje siempre sale con 2 decimales")
    void porcentajeConDosDecimales(String enBd, String esperado) {
        BigDecimal porcentaje = DiscountMapper.toResponse(
                new DiscountEntity(1L, new BigDecimal(enBd), "X")).getPercentage();

        assertThat(porcentaje.scale()).isEqualTo(2);
        assertThat(porcentaje.toPlainString()).isEqualTo(esperado);
    }

    @Test
    @DisplayName("Una página de JPA se convierte en DiscountPage con sus metadatos")
    void mapeaPagina() {
        List<DiscountEntity> contenido = List.of(
                new DiscountEntity(1L, BigDecimal.ONE, "A"),
                new DiscountEntity(2L, BigDecimal.TEN, "B"));

        DiscountPage pagina = DiscountMapper.toPage(new PageImpl<>(contenido, PageRequest.of(1, 2), 5));

        assertThat(pagina.getItems()).extracting(DiscountResponse::getDescription).containsExactly("A", "B");
        assertThat(pagina.getPage()).isEqualTo(1);
        assertThat(pagina.getSize()).isEqualTo(2);
        assertThat(pagina.getTotalElements()).isEqualTo(5L);
        assertThat(pagina.getTotalPages()).isEqualTo(3);
    }
}
