package com.bootcamp.catalogo.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * CatalogResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-11-23T13:55:50.198780170-06:00[America/Mexico_City]")
public class CatalogResponse {

  private Long productId;

  private String name;

  private BigDecimal basePrice;

  private Integer stock;

  public CatalogResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CatalogResponse(Long productId, String name, BigDecimal basePrice, Integer stock) {
    this.productId = productId;
    this.name = name;
    this.basePrice = basePrice;
    this.stock = stock;
  }

  public CatalogResponse productId(Long productId) {
    this.productId = productId;
    return this;
  }

  /**
   * Get productId
   * @return productId
  */
  @NotNull 
  @Schema(name = "productId", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("productId")
  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public CatalogResponse name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  @NotNull @Size(min = 1, max = 120) 
  @Schema(name = "name", example = "Laptop Pro 14", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CatalogResponse basePrice(BigDecimal basePrice) {
    this.basePrice = basePrice;
    return this;
  }

  /**
   * Precio base en MXN, con 2 decimales
   * minimum: 0
   * @return basePrice
  */
  @NotNull @Valid @DecimalMin("0") 
  @Schema(name = "basePrice", example = "25000.0", description = "Precio base en MXN, con 2 decimales", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("basePrice")
  public BigDecimal getBasePrice() {
    return basePrice;
  }

  public void setBasePrice(BigDecimal basePrice) {
    this.basePrice = basePrice;
  }

  public CatalogResponse stock(Integer stock) {
    this.stock = stock;
    return this;
  }

  /**
   * Get stock
   * minimum: 0
   * @return stock
  */
  @NotNull @Min(0) 
  @Schema(name = "stock", example = "15", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("stock")
  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CatalogResponse catalogResponse = (CatalogResponse) o;
    return Objects.equals(this.productId, catalogResponse.productId) &&
        Objects.equals(this.name, catalogResponse.name) &&
        Objects.equals(this.basePrice, catalogResponse.basePrice) &&
        Objects.equals(this.stock, catalogResponse.stock);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productId, name, basePrice, stock);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CatalogResponse {\n");
    sb.append("    productId: ").append(toIndentedString(productId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    basePrice: ").append(toIndentedString(basePrice)).append("\n");
    sb.append("    stock: ").append(toIndentedString(stock)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

