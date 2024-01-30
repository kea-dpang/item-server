package kea.dpang.item.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

/**
 * Swagger(Spring Docs) 설정
 */
@OpenAPIDefinition(
        info = @Info(title = "DPANG ITEM API 명세서",
                description = "DPANG ITEM API 명세서",
                version = "v0.1.7")
)
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    public OpenApiCustomizer securityBudiler() {
        return openApi -> openApi.addSecurityItem(new SecurityRequirement().addList("Access Token"))
                .getComponents()
                .addSecuritySchemes("Access Token", new SecurityScheme()
                        .name(HttpHeaders.AUTHORIZATION)
                        .type(SecurityScheme.Type.HTTP)
                        .in(SecurityScheme.In.HEADER)
                        .bearerFormat("JWT")
                        .scheme("bearer"));
    }

    @Bean
    public GroupedOpenApi openApi() {
        String[] paths = {"/**"};

        return GroupedOpenApi.builder()
                .group("DPANG ITEM 서비스 API")
                .pathsToMatch(paths)
                .addOpenApiCustomizer(securityBudiler())
                .build();
    }
}
