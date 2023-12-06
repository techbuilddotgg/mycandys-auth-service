package com.feri.sua.auth.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

        @Value("${application.swagger.server.url}")
        private String swaggerServerUrl;

        @Value("${server.port}")
        private String serverPort;

        @Value("${spring.profiles.active}")
        private String activeProfile;

        @Bean
        public OpenAPI defineOpenApi() {
                return new OpenAPI()
                        .info(defineInfo())
                        .addServersItem(defineServer())
                        .components(new Components().addSecuritySchemes("Bearer Authentication", defineSecurityScheme()));
        }


        private Server defineServer() {
                if(activeProfile.equalsIgnoreCase("production") || activeProfile.equalsIgnoreCase("prod"))
                        return new Server().url(swaggerServerUrl)
                                .description("Default server");

                return new Server().url("http://localhost:" + serverPort)
                        .description("Default server");
        }

        private Info defineInfo() {
                return new Info()
                        .title("MyCandys Authentification API")
                        .description("Authentification API for MyCandys application")
                        .version("1.0");
        }

        private SecurityScheme defineSecurityScheme() {
                return new SecurityScheme()
                        .name("Bearer Authentication")
                        .description("JWT auth description")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER);

        }
}