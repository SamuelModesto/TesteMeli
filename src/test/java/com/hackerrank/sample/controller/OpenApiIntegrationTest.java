package com.hackerrank.sample.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OpenApiIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldAccessSwaggerUi() {
        ResponseEntity<String> response = restTemplate.getForEntity("/swagger-ui.html", String.class);
        
        assertTrue(response.getStatusCode().is3xxRedirection() || response.getStatusCode() == HttpStatus.OK);
    }

    @Test
    void shouldAccessApiDocs() {
        ResponseEntity<String> response = restTemplate.getForEntity("/v3/api-docs", String.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("API de Produtos - HackerRank Sample"));
        assertTrue(response.getBody().contains("/api/v1/products"));
    }
}
