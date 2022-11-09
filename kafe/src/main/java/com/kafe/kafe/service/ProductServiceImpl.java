package com.kafe.kafe.service;

import com.kafe.kafe.dto.request.ProductRequestDTO;
import com.kafe.kafe.dto.response.ProductResponseDTO;
import com.kafe.kafe.entity.Product;
import com.kafe.kafe.entity.ProductCategory;
import com.kafe.kafe.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return getProductResponseDTOS(products);
    }

    @Override
    public ProductResponseDTO saveProduct(ProductRequestDTO product) {
        Product productToSave = modelMapper.map(product, Product.class);
        computeTotalPrice(productToSave);
        ProductResponseDTO result = modelMapper.map(productRepository.save(productToSave), ProductResponseDTO.class);
        result.setFinalPrice(computeTotalPrice(productToSave));
        return result;
    }

    @Override
    public ProductResponseDTO updateProduct(ProductRequestDTO product, Integer id) {
        Product productToUpdate = modelMapper.map(product, Product.class);
        Optional<Product> productInDatabase = productRepository.findById(id);
        Product updatedProduct = null;
        if(productInDatabase.isPresent()) {
            productInDatabase.get().setCategory(productToUpdate.getCategory());
            productInDatabase.get().setDescription(productToUpdate.getDescription());
            productInDatabase.get().setName(productToUpdate.getName());
            productInDatabase.get().setPriceByCm(productToUpdate.getPriceByCm());
            productInDatabase.get().setHeight(productToUpdate.getHeight());
            productInDatabase.get().setWidth(productToUpdate.getWidth());
            productInDatabase.get().setImageLink(productToUpdate.getImageLink());
            updatedProduct = productRepository.save(productInDatabase.get());
            ProductResponseDTO result = modelMapper.map(updatedProduct, ProductResponseDTO.class);
            result.setFinalPrice(computeTotalPrice(updatedProduct));
            return result;
        }
        return null;
    }

    @Override
    public Map<String, Integer> deleteProduct(Integer id) {
        productRepository.deleteById(id);
        return Map.of("Product deleted", id);
    }

    @Override
    public List<ProductResponseDTO> getAllProductsByCategory(ProductCategory category) {
        List<Product> products = productRepository.findByCategory(category);
        return getProductResponseDTOS(products);
    }

    @Override
    public ProductResponseDTO getProductById(Integer id) {
        Product product = productRepository.findById(id).orElse(null);
        ProductResponseDTO result = new ProductResponseDTO();
        if (product != null) {
            result = modelMapper.map(product, ProductResponseDTO.class);
            result.setFinalPrice(computeTotalPrice(product));
        }

        return result;
    }

    @Override
    public List<ProductResponseDTO> getAllProductsByPriceRange(BigDecimal max, BigDecimal min) {
        List<Product> products = productRepository.findByPriceByCmBetween(max, min);
        return getProductResponseDTOS(products);
    }

    private BigDecimal computeTotalPrice(Product product) {
        return product.getPriceByCm().multiply((product.getWidth().multiply(product.getHeight())));
    }

    private List<ProductResponseDTO> getProductResponseDTOS(List<Product> products) {
        List<ProductResponseDTO> response = new ArrayList<>();
        for (Product product : products) {
            ProductResponseDTO result = modelMapper.map(product, ProductResponseDTO.class);
            result.setFinalPrice(computeTotalPrice(product));
            response.add(result);
        }
        return response;
    }
}