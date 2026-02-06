package com.hackerrank.sample.controller;

import com.hackerrank.sample.dto.ProductResponseDto;
import com.hackerrank.sample.exception.ResourceNotFoundException;
import com.hackerrank.sample.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
@ComponentScan(basePackages = "com.hackerrank.sample.infrastructure.exception")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void whenProductExists_thenReturnOk() throws Exception {
        ProductResponseDto responseDto = new ProductResponseDto(
            "PRD-1", "Title", null, "NEW", null, null, null, null, null, null, null
        );
        when(productService.findProductById("PRD-1")).thenReturn(responseDto);

        mockMvc.perform(get("/api/v1/products/PRD-1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value("PRD-1"))
                .andExpect(jsonPath("$.title").value("Title"));
    }

    @Test
    void whenProductDoesNotExist_thenReturnNotFoundWithProblemDetails() throws Exception {
        when(productService.findProductById("NON-EXISTENT"))
            .thenThrow(new ResourceNotFoundException("O recurso solicitado não foi encontrado."));

        mockMvc.perform(get("/api/v1/products/NON-EXISTENT")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("O recurso solicitado não foi encontrado."))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value("O recurso solicitado não foi encontrado."))
                .andExpect(jsonPath("$.instance").value("/api/v1/products/NON-EXISTENT"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
