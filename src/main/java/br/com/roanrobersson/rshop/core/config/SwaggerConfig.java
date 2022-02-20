package br.com.roanrobersson.rshop.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
			    .select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
				.paths(PathSelectors.any())
				.build()		
				.useDefaultResponseMessages(false)
				.tags(new Tag("Category", ""),
						new Tag("File", ""),
						new Tag("Product", ""),
						new Tag("Role", ""),
						new Tag("Privilege", ""),
						new Tag("Role Privilege", ""),
						new Tag("User Role", ""),
						new Tag("UserAddress", ""),
						new Tag("User", ""));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("rShop API")
				.description("Project in development").version("1.0.0")
				.license("MIT License")
				.licenseUrl("https://mit-license.org/")
				.contact(new Contact("Roan Oliveira",
						"https://www.linkedin.com/in/roanoliveira", "roanrobersson@gmail.com"))
				.build();
	}
}
