package com.motohive.vehicleservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
	
	@Value("${server.port}")
	private int serverPort;
	private static final String SECURITY_SCHEME_NAME = "bearerAuth";
	
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				       .info(new Info()
						             .title("MotoHive Vehicle Service API")
						             .description("Handles Vehicle addition, deletion, and modification operations")
						             .version("1.0.0")
						             .contact(new Contact()
								                      .name("MotoHive")
								                      .email("dev@motohive.com")))
				       .servers(List.of(
						       new Server()
								       .url("http://localhost:" + serverPort)
								       .description("Local development server")));
//				       // ── JWT security scheme — used in security phase
//				       .addSecurityItem(new SecurityRequirement()
//						                        .addList(SECURITY_SCHEME_NAME))
//				       .components(new Components()
//						                   .addSecuritySchemes(SECURITY_SCHEME_NAME,
//								                   new SecurityScheme()
//										                   .name(SECURITY_SCHEME_NAME)
//										                   .type(SecurityScheme.Type.HTTP)
//										                   .scheme("bearer")
//										                   .bearerFormat("JWT")
//										                   .description("Paste your JWT access token here")));
	}
	
}