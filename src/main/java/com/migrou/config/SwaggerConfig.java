package com.migrou.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Bean
    public Docket appSwaggerConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.migrou.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaInfo());

    }

    private ApiInfo metaInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Apis Migrou",
                "Apis Migrou",
                "1.0",
                "Termos do serviço",
                new Contact("Rafael Colvara","www.migrou.com.br", "suporte@migrou.com.br"),
                "Apache licence",
                "Http://apache.com" );

        return apiInfo;
    }

}
