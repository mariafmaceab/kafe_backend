package com.kafe.kafe.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kafe.kafe.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class
ProductResponseDTO {
    private String name;
    private ProductCategory category;
    private String description;
    private BigDecimal finalPrice;
    private String imageLink;
}
