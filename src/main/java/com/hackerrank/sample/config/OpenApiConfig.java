package com.hackerrank.sample.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de Produtos - HackerRank Sample")
                .version("1.0.0")
                .description("Documentação dos endpoints para gerenciamento de produtos.")
                .contact(new Contact()
                    .name("Suporte")
                    .email("suporte@example.com")));
    }
}
