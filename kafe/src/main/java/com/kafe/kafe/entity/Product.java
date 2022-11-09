package com.kafe.kafe.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Data @NoArgsConstructor @AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private ProductCategory category;
    private BigDecimal priceByCm;
    private String description;
    private BigDecimal height;
    private BigDecimal width;
    private String imageLink;
}
