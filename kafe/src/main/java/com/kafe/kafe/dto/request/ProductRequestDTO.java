package com.kafe.kafe.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kafe.kafe.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data @NoArgsConstructor @AllArgsConstructor
public class ProductRequestDTO {
    @NotNull(message = "El nombre no puede estar vacío")
    private String name;

    @NotNull(message = "La categoría no puede estar vacía")
    private ProductCategory category;

    @NotNull(message = "El precio no puede estar vacío")
    @Min(value=0,message = "El precio no puede ser menor a 0")
    private BigDecimal priceByCm;

    @Size(min = 10, max = 255, message = "La descripcion debe tener entre 10 y 255 caracteres")
    private String description;

    @NotNull(message = "Agregue una altura")
    private Double height;

    @NotNull(message = "Agregue un ancho")
    private Double width;

    @NotNull(message = "Agregue un link de la imagen del producto")
    private String imageLink;
}
