package com.api.wiveService.WineService.config.security;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Api WineService",
                version = "v1",
                description = "WineService é uma API destinada a alimentar uma rede social de vinhos, " +
                        "oferecendo funcionalidades como cadastro de usuários, gerenciamento de vinhos (cadastro, edição e inativação), " +
                        "além de permitir a avaliação e comentários sobre os vinhos cadastrados. " +
                        "Desenvolvida em Java 17, esta API utiliza Spring Boot e JPA, garantindo robustez e escalabilidade para o serviço." +
                        " " +
                        "- Para conseguir as credencias entre em contrato (21) 97306-3532",
                contact = @Contact(name = "Arthur Felix", email = "dev.felixarthur@gmail.com")
        ),
        externalDocs = @ExternalDocumentation(description = "Definições adicionais aqui")
)

@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("API_WineService")
                .packagesToScan("com.api.wiveService.WineService")
                .pathsToMatch("/**")
                .build();
    }
}
