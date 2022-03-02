package br.com.roanrobersson.rshop.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApi {
	
	  @Bean
	  public OpenAPI springShopOpenAPI() {
	      return new OpenAPI()
	              .info(new Info().title("rShop API")
	              .description("Project in development")
	              .version("v0.0.1")
	              .license(new License().name("MIT").url("https://mit-license.org/")))
	              .externalDocs(new ExternalDocumentation()
	              .description("Roan Oliveira")
	              .url("https://www.linkedin.com/in/roanoliveira"));
	  }
}
