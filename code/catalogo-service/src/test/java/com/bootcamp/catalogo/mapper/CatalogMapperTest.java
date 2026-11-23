package com.bootcamp.catalogo.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.bootcamp.catalogo.entity.CatalogEntity;
import com.bootcamp.catalogo.model.CatalogPage;
import com.bootcamp.catalogo.model.CatalogResponse;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/** Prueba unitaria pura: sin Spring, milisegundos por prueba. */
@DisplayName("CatalogMapper: entidad -> DTOs del contrato")
class CatalogMapperTest {

    @Test
    @DisplayName("Copia todos los campos de la entidad al DTO")
    void mapeaTodosLosCampos() {
        CatalogEntity entidad = new CatalogEntity(1L, "Laptop Pro 14", new BigDecimal("25000.00"), 15);

        CatalogResponse dto = CatalogMapper.toResponse(entidad);

        assertThat(dto.getProductId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Laptop Pro 14");
        assertThat(dto.getBasePrice()).isEqualByComparingTo("25000.00");
        assertThat(dto.getStock()).isEqualTo(15);
    }

    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({
        "25000,    25000.00",   // entero: se completan los decimales
        "450.5,    450.50",
        "19.994,   19.99",      // redondeo hacia abajo
        "19.995,   20.00",      // HALF_UP: .5 redondea hacia arriba
        "0.001,    0.00"
    })
    @DisplayName("El precio siempre sale con 2 decimales (lo promete el contrato)")
    void precioConDosDecimales(String precioEnBd, String precioEsperado) {
        CatalogEntity entidad = new CatalogEntity(1L, "X", new BigDecimal(precioEnBd), 1);

        BigDecimal precio = CatalogMapper.toResponse(entidad).getBasePrice();

        assertThat(precio.scale()).isEqualTo(2);
        assertThat(precio.toPlainString()).isEqualTo(precioEsperado);
    }

    @Test
    @DisplayName("Una página de JPA se convierte en CatalogPage con sus metadatos")
    void mapeaPagina() {
        List<CatalogEntity> contenido = List.of(
                new CatalogEntity(1L, "A", BigDecimal.ONE, 1),
                new CatalogEntity(2L, "B", BigDecimal.TEN, 2));

        CatalogPage pagina = CatalogMapper.toPage(new PageImpl<>(contenido, PageRequest.of(1, 2), 5));

        assertThat(pagina.getItems()).extracting(CatalogResponse::getName).containsExactly("A", "B");
        assertThat(pagina.getPage()).isEqualTo(1);
        assertThat(pagina.getSize()).isEqualTo(2);
        assertThat(pagina.getTotalElements()).isEqualTo(5L);
        assertThat(pagina.getTotalPages()).isEqualTo(3);
    }
}
