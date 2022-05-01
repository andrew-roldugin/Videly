package ru.vsu.csf.group7.config;

import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setType(SecurityScheme.Type.APIKEY);
        securityScheme.setName("Authorization");
        securityScheme.setIn(SecurityScheme.In.HEADER);


        return new OpenAPI()
                .schemaRequirement("bearer_token", securityScheme)
                .info(
                        new Info()
                                .description("REST API сервер для мобильного приложения Videly")
                                .title("Videly REST API")
                                .version("1.0.0")
                );
    }

}