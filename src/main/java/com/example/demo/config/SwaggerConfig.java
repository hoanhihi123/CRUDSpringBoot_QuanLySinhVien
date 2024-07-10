package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.Value;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author: Hai, Hoan
 *
 */
@Configuration
public class SwaggerConfig {

    /**
     * Chỉ định đường dẫn mà swagger sẽ quét
     *
     * @return cấu hình chỉ định đường dẫn cho swagger thực hiện quét
     *
     */
    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("api service")
                .pathsToMatch("/**")  // chỉ định đường dẫn endpoint mà swagger sẽ quét
        .build();
    }

    /**
     * Cấu hình lại mô tả document hiển thị khi sử dụng Swagger
     *
     * @return các thông tin được cấu hình lại
     *
     */
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info()
                .title("API service document")
                .version("1.2.0")
                .description("The document describes the API of the student management system"))
                .servers(List.of(new Server().url("http://localhost:8080").description("Server Test")));
    }

//    @Bean
//    public GroupedOpenApi groupedOpenApi(){
//        return GroupedOpenApi.builder()
//                .group("api service document")
//                .packagesToScan("com.example.demo.controller")  // chỉ định các gói mà swagger sẽ quét
//                .build();
//    }

}
