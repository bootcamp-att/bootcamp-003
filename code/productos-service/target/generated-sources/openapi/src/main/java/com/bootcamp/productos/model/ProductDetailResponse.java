package com.bootcamp.productos.model;

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
 * ProductDetailResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-11-23T14:16:15.240223673-06:00[America/Mexico_City]")
public class ProductDetailResponse {

  private Long id;

  private String name;

  private BigDecimal basePrice;

  private BigDecimal discountPercentage;

  private BigDecimal finalPrice;

  private Integer stock;

  public ProductDetailResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ProductDetailResponse(Long id, String name, BigDecimal basePrice, BigDecimal discountPercentage, BigDecimal finalPrice, Integer stock) {
    this.id = id;
    this.name = name;
    this.basePrice = basePrice;
    this.discountPercentage = discountPercentage;
    this.finalPrice = finalPrice;
    this.stock = stock;
  }

  public ProductDetailResponse id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @NotNull 
  @Schema(name = "id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ProductDetailResponse name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  @NotNull @Size(min = 1) 
  @Schema(name = "name", example = "Laptop Pro 14", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ProductDetailResponse basePrice(BigDecimal basePrice) {
    this.basePrice = basePrice;
    return this;
  }

  /**
   * Precio base en MXN, 2 decimales
   * minimum: 0
   * @return basePrice
  */
  @NotNull @Valid @DecimalMin("0") 
  @Schema(name = "basePrice", example = "25000.0", description = "Precio base en MXN, 2 decimales", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("basePrice")
  public BigDecimal getBasePrice() {
    return basePrice;
  }

  public void setBasePrice(BigDecimal basePrice) {
    this.basePrice = basePrice;
  }

  public ProductDetailResponse discountPercentage(BigDecimal discountPercentage) {
    this.discountPercentage = discountPercentage;
    return this;
  }

  /**
   * Porcentaje aplicado (0 si no hay descuento o si descuento-service no responde)
   * minimum: 0
   * maximum: 100
   * @return discountPercentage
  */
  @NotNull @Valid @DecimalMin("0") @DecimalMax("100") 
  @Schema(name = "discountPercentage", example = "10.0", description = "Porcentaje aplicado (0 si no hay descuento o si descuento-service no responde)", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("discountPercentage")
  public BigDecimal getDiscountPercentage() {
    return discountPercentage;
  }

  public void setDiscountPercentage(BigDecimal discountPercentage) {
    this.discountPercentage = discountPercentage;
  }

  public ProductDetailResponse finalPrice(BigDecimal finalPrice) {
    this.finalPrice = finalPrice;
    return this;
  }

  /**
   * basePrice - basePrice * discountPercentage / 100, redondeado a 2 decimales (HALF_UP)
   * minimum: 0
   * @return finalPrice
  */
  @NotNull @Valid @DecimalMin("0") 
  @Schema(name = "finalPrice", example = "22500.0", description = "basePrice - basePrice * discountPercentage / 100, redondeado a 2 decimales (HALF_UP)", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("finalPrice")
  public BigDecimal getFinalPrice() {
    return finalPrice;
  }

  public void setFinalPrice(BigDecimal finalPrice) {
    this.finalPrice = finalPrice;
  }

  public ProductDetailResponse stock(Integer stock) {
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
    ProductDetailResponse productDetailResponse = (ProductDetailResponse) o;
    return Objects.equals(this.id, productDetailResponse.id) &&
        Objects.equals(this.name, productDetailResponse.name) &&
        Objects.equals(this.basePrice, productDetailResponse.basePrice) &&
        Objects.equals(this.discountPercentage, productDetailResponse.discountPercentage) &&
        Objects.equals(this.finalPrice, productDetailResponse.finalPrice) &&
        Objects.equals(this.stock, productDetailResponse.stock);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, basePrice, discountPercentage, finalPrice, stock);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductDetailResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    basePrice: ").append(toIndentedString(basePrice)).append("\n");
    sb.append("    discountPercentage: ").append(toIndentedString(discountPercentage)).append("\n");
    sb.append("    finalPrice: ").append(toIndentedString(finalPrice)).append("\n");
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

