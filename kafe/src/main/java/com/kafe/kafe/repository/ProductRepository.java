package com.kafe.kafe.repository;

import com.kafe.kafe.entity.Product;
import com.kafe.kafe.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByPriceByCmBetween(BigDecimal priceByCmStart, BigDecimal priceByCmEnd);
    List<Product> findByCategory(ProductCategory category);
}
