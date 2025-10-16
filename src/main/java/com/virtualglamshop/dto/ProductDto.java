package com.virtualglamshop.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProductDto {
  @NotBlank @Size(max=120) public String name;
  @NotBlank @Size(max=80)  public String brand;
  @NotBlank @Size(max=60)  public String category;
  @NotNull @DecimalMin("0.00") public BigDecimal price;
  @NotNull @Min(0) public Integer stockQty;
  @Size(max=255) public String imageURL;
}
