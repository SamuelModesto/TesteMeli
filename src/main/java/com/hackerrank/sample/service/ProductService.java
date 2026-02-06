package com.hackerrank.sample.service;

import com.hackerrank.sample.dto.*;
import com.hackerrank.sample.exception.ResourceNotFoundException;
import com.hackerrank.sample.mapper.ProductMapper;
import com.hackerrank.sample.repository.ProductRepository;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private static final String PRODUCT_NOT_FOUND_MSG = "O recurso solicitado não foi encontrado.";
    private static final String FETCHING_PRODUCT_FROM_DB = "Buscando produto no banco de dados com ID: {}";

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Cacheable(value = "products", key = "#id")
    @Timed(value = "product.search.duration", description = "Tempo de busca de produtos por ID", percentiles = {0.5, 0.95, 0.99})
    @Transactional(readOnly = true)
    public ProductResponseDto findProductById(String id) {
        logger.warn(FETCHING_PRODUCT_FROM_DB, id);
        return productRepository.findById(id)
                .map(productMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND_MSG));
    }
}
