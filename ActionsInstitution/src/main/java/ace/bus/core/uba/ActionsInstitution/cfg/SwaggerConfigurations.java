/**
 * 
 */
package ace.bus.core.uba.ActionsInstitution.cfg;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Zero
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfigurations {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)).paths(PathSelectors.any())
				.build()
				.apiInfo(new ApiInfo("Unibanca", "Microservice", "1.0", "Acciones sobre Instituciones",
						new Contact("Unibanca", " .", "Empowered by Synopsis "),
						"Uso exclusivo para Unibanca", " .", Collections.emptyList()));

	}
	
}
