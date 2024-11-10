package com.yokke.base.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class OpenApiConfig {

    @Value("${app.api.version}")
    private String apiVersion;

    @Value("${app.api.server.local.url}")
    private String localUrl;

    @Value("${app.api.server.dev.url}")
    private String devUrl;

    @Value("${app.api.server.prod.url}")
    private String prodUrl;

    @Primary
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new io.swagger.v3.oas.models.info.Info()
                                .title("Transactions Service API")
                                .version(apiVersion)
                                .description("Transactions Service API Documentation")
                )
                .addServersItem(
                        new io.swagger.v3.oas.models.servers.Server()
                                .description("Local ENV")
                                .url(localUrl)
                )
                .addServersItem(
                        new io.swagger.v3.oas.models.servers.Server()
                                .description("Dev ENV")
                                .url(devUrl)
                )
                .addServersItem(
                        new io.swagger.v3.oas.models.servers.Server()
                                .description("Prod ENV")
                                .url(prodUrl)
                )
                .addSecurityItem(
                        new io.swagger.v3.oas.models.security.SecurityRequirement()
                                .addList("bearerAuth")
                )
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                                        .description("JWT auth description")
                        ));
    }
}