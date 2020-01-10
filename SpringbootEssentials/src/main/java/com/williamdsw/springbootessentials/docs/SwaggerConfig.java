package com.williamdsw.springbootessentials.docs;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.Parameter;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig 
{
    //------------------------------------------------------------------------//
    // BEANS
	
	@Bean
	public Docket apiDoc ()
	{
		final String basePackage = "com.williamdsw.springbootessentials.endpoint";
		return new Docket(DocumentationType.SWAGGER_2)
				   .select ()
				   .apis(RequestHandlerSelectors.basePackage (basePackage))
				   .paths (PathSelectors.regex ("/v1.*"))
				   .build ()
				   .globalOperationParameters(Collections.singletonList (this.buildAuthorizationParameter ()))
				   .apiInfo(this.metaData ());
	}
	
    //------------------------------------------------------------------------//
    // HELPER FUNCTIONS
	
	private ApiInfo metaData ()
	{
		Contact contact = new Contact("William Santos", "https://www.https://github.com/williamdsw", "williamdsw@outlook.com");
		return new ApiInfoBuilder ()
				   .title ("Spring Boot Essentials By DevDojo")
				   .description ("The best spring course out there")
				   .version ("1.0")
				   .contact (contact)
				   .license("Apache License Version 2.0")
				   .licenseUrl("https://www.apache.org/license/LICENSE-2.0")
				   .build ();
	}
	
	private Parameter buildAuthorizationParameter ()
	{
		return new ParameterBuilder ()
				   .name ("Authorization")
				   .description ("Bearer token")
				   .modelRef (new ModelRef ("string"))
				   .parameterType ("header")
				   .required (true)
				   .build ();
	}
}