package com.hackerrank.sample.config;

import com.hackerrank.sample.repository.ProductRepository;
import com.hackerrank.sample.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class CacheWarmer {
    private static final Logger logger = LoggerFactory.getLogger(CacheWarmer.class);
    private static final String WARMING_UP_CACHE = "Iniciando aquecimento do cache (Cache Warming) para os 30 primeiros produtos...";
    private static final String CACHE_WARMING_COMPLETED = "Aquecimento do cache concluído com sucesso. {} produtos foram carregados.";

    private final ProductService productService;
    private final ProductRepository productRepository;

    public CacheWarmer(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void warmUp() {
        logger.info(WARMING_UP_CACHE);
        List<String> topProductIds = productRepository.findFirstProductIds(PageRequest.of(0, 30));
        topProductIds.forEach(productService::findProductById);
        logger.info(CACHE_WARMING_COMPLETED, topProductIds.size());
    }
}
