package kr.co.anna.api.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.security.SecuritySchemes
import org.springframework.context.annotation.Configuration

/**
 * Open API (Swagger) 설정
 */
@Configuration
@OpenAPIDefinition(
    security = [
        SecurityRequirement(name = "JWT"),
    ]
)
@SecuritySchemes(
    SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "JWT",
        description = "JWT Bearer Token 입력",
        scheme = "bearer",
        bearerFormat = "JWT"
    ),
)
class OpenApiConfig {}
