package com.example.todomysql.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI todoOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Todo API")
                .description("Spring Boot REST API for Todo Management"));
    }
} 