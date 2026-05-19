package org.example.bank2.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                                    .addSecuritySchemes("bearerAuth",
                                                        new SecurityScheme()
                                                                .name("bearerAuth")
                                                                .type(SecurityScheme.Type.HTTP)
                                                                .scheme("bearer")
                                                                .bearerFormat("JWT")));
    }

    @Bean
    public OpenApiCustomizer loginWithoutSecurity() {
        return openApi -> {
            PathItem loginPath = openApi.getPaths().get("/oauth/login");

            if (loginPath != null && loginPath.getPost() != null) {
                loginPath.getPost().setSecurity(List.of());
            }
        };
    }
}