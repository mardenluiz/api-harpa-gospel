package com.mardenluiz.harpa.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Harpa Cristã API",
                version = "v1",
                description = """
                        API REST para consulta de hinos da Harpa Cristã.
                        
                        Recursos disponíveis:
                        • Consulta de hinos por número;
                        • Consulta por título;
                        • Listagem paginada de hinos;
                        • Consulta de coro dos hinos;
                        • Consulta de áudios hospedados no Cloudflare R2;
                        • Gerenciamento de cache Redis.
                        
                        Esta API foi desenvolvida utilizando Java 25, Spring Boot,
                        PostgreSQL, Redis e Cloudflare R2.
                        """,
                contact = @Contact(
                        name = "Marden Luiz",
                        url = "https://github.com/mardenluiz"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(
                        description = "Servidor Local",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Servidor de Produção",
                        url = "https://api.harpacrista.com.br"
                )
        }
)
public class OpenApiConfig {

    @Bean
    public OpenAPI harpaCristaOpenAPI() {

        return new OpenAPI()

                .externalDocs(new ExternalDocumentation()
                        .description("Repositório Oficial")
                        .url("https://github.com/mardenluiz/api-harpa-gospel"))

                .addTagsItem(new Tag()
                        .name("Hinos")
                        .description("Operações relacionadas aos hinos da Harpa Cristã"))

                .addTagsItem(new Tag()
                        .name("Áudios")
                        .description("Operações relacionadas aos áudios dos hinos"))

                .addTagsItem(new Tag()
                        .name("Versículos")
                        .description("Operações relacionadas às letras dos hinos"))

                .addTagsItem(new Tag()
                        .name("Cache")
                        .description("Operações de gerenciamento do cache Redis"));
    }

}