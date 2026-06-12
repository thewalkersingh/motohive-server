package com.motohive.paymentservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
	
	private static final String SECURITY_SCHEME_NAME = "bearerAuth";
	
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				       .info(new Info()
						             .title("MotoHive User Service API")
						             .description("Handles user registration, authentication, profiles and appointments")
						             .version("1.0.0")
						             .contact(new Contact()
								                      .name("MotoHive")
								                      .email("dev@motohive.com")))
				       // ── JWT security scheme ──────────────────────
				       .addSecurityItem(new SecurityRequirement()
						                        .addList(SECURITY_SCHEME_NAME))
				       .components(new Components()
						                   .addSecuritySchemes(SECURITY_SCHEME_NAME,
								                   new SecurityScheme()
										                   .name(SECURITY_SCHEME_NAME)
										                   .type(SecurityScheme.Type.HTTP)
										                   .scheme("bearer")
										                   .bearerFormat("JWT")
										                   .description("Paste your JWT access token here")));
	}
	
}