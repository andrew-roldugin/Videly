package ru.vsu.csf.group7.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
//        Server serversItem = new Server();
        return new OpenAPI()
//                .addServersItem(serversItem)
                .info(
                        new Info()
                                .description("REST API сервер для мобильного приложения Videly")
                                .title("Videly REST API")
                                .version("1.0.0")
                );
    }

}