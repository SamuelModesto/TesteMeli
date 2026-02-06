package com.hackerrank.sample.controller;

import com.hackerrank.sample.dto.ProductResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DatabaseSeederIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldSeed100ProductsOnStartup() {
        // Como o endpoint de listagem foi removido, validamos que um produto específico criado pelo seeder existe
        ResponseEntity<ProductResponseDto> response = restTemplate.getForEntity("/api/v1/products/PROD001", ProductResponseDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("PROD001", response.getBody().productId());
        assertNotNull(response.getBody().title());
        assertNotNull(response.getBody().category());
        assertNotNull(response.getBody().sellerPreview());
    }
}
