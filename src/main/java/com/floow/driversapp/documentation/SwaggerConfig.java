package com.floow.driversapp.documentation;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
@AllArgsConstructor
class SwaggerConfig {

    private final MessageSource messageSource;

    @Bean
    public Docket driversApiV1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("drivers-api")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.floow.driversapp"))
                .paths(or(
                        regex("/driver/.*"),
                        regex("/drivers"),
                        regex("/drivers/.*"),
                        regex("/health.*"))
                ).build()
                .apiInfo(new ApiInfoBuilder()
                        .title("Drivers App The Floow company")
                        .version("1.0")
                        .contact(new Contact("Drivers-App", "", "cecot.karol@gmail.com"))
                        .description(getMessages().getMessage("drivers.api.swagger.description"))
                        .build());
    }

    private MessageSourceAccessor getMessages() {
        return new MessageSourceAccessor(messageSource);
    }
}