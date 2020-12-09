package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.configs;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
@EnableAutoConfiguration
public class SwaggerConfig  {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .apis(RequestHandlerSelectors.basePackage("org.egen.solutions.coding.assignment.rakshit"))
                .build();
    }

    private ApiInfo apiInfo() {
        final Contact contact = new Contact("Rakshit Reddy Adaboyna", null, "rakshitreddy7@gmail.com");

        return new ApiInfo("Oder Processing API",
                "APIs for multiple operations on an order",
                "v1",
                "open-source",
                contact,
                "MIT Licence",
                "www.google.com",
                Collections.emptyList());
    }
}

