package com.cronoseuropa.api.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Cronos Europa API")
                .version("1.0.0");

        return new OpenAPI().info(info);
    }
}
