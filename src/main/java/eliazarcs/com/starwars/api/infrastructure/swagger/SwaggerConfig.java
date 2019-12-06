package eliazarcs.com.starwars.api.infrastructure.swagger;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {                
	    return new Docket(DocumentationType.SWAGGER_2)          
	      .select()
	      .apis(RequestHandlerSelectors.basePackage("eliazarcs.com.starwars.api.controller"))
	      .paths(PathSelectors.any())
	      .build()
	      .apiInfo(apiInfo());
	}
	 
	private ApiInfo apiInfo() {
		
		Contact contact = new Contact("Eliazar de Carvalho Silva", 
				"https://www.linkedin.com/in/eliazar-carvalho-silva-36510485",
				"eliazarcs@gmail.com");
		ApiInfo info = new ApiInfo("Star Wars API", "API responsável por prover informações diferentes tipos de informações sobre a série Star Wars", 
				"1.0", "Terms of service", contact, "Apache License","www.starwars.com.br/license", Collections.emptyList());
		return info;
	}
}
