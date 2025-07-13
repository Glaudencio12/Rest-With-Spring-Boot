package br.com.Glaudencio12.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    OpenAPI cuntomOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Rest With Spring Boot and Java")
                        .version("V1")
                        .description("RESTful API created during my learning in Spring Boot.")
                );
    }
}
