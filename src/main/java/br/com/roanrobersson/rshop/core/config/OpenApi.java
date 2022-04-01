package br.com.roanrobersson.rshop.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration

@SecurityScheme(name = "OAuth2", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(password = @OAuthFlow(tokenUrl = "${springdoc.oAuthFlow.authorizationUrl}", refreshUrl = "${springdoc.oAuthFlow.authorizationUrl}")))
//	@SecurityScheme(
//    name = "bearer",
//    type = SecuritySchemeType.HTTP,
//    scheme = "bearer"
//	),        		

public class OpenApi {

	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("rShop API").description("Project in development").version("v0.0.1")
						.license(new License().name("MIT").url("https://mit-license.org/")))
				.externalDocs(new ExternalDocumentation().description("Roan Oliveira")
						.url("https://www.linkedin.com/in/roanoliveira"));
	}
}
