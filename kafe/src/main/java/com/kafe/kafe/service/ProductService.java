package com.kafe.kafe.service;

import com.kafe.kafe.dto.request.ProductRequestDTO;
import com.kafe.kafe.dto.response.ProductResponseDTO;
import com.kafe.kafe.entity.Product;
import com.kafe.kafe.entity.ProductCategory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProductService {

    ProductResponseDTO getProductById(Integer id);
    List<ProductResponseDTO> getAllProductsByPriceRange(BigDecimal max, BigDecimal min);
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO saveProduct(ProductRequestDTO product);
    ProductResponseDTO updateProduct(ProductRequestDTO product, Integer id);
    Map<String, Integer> deleteProduct(Integer id);
    List<ProductResponseDTO> getAllProductsByCategory(ProductCategory category);

}
