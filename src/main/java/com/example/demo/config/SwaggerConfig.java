package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.validation.Valid;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI api(@Value("${open.api.title}") String title,
                       @Value("${open.api.version}") String version,
                       @Value("${open.api.description}") String description,
                       @Value("${open.api.serverUrl}") String serverUrl,
                       @Value("${open.api.serverName}") String serverName) {
        return new OpenAPI().info(new Info().title("API-service document")
                .version("v1.0.0").description("api quản lý sinh viên")
                .license(new License().name("API License").url("http://domain.vn/license")))
                .servers(List.of(new Server().url("http://localhost:8080").description("Server-Test")));
    }
    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder().group("api quan ly sinh vien").packagesToScan("com.example.demo.controller").build();
    }
}
