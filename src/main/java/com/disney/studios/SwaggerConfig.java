/*
 * Copyright (c) 2019. copyright text here
 */

package com.disney.studios;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final Contact DEFAULT_CONTACT = new Contact(
        "Jane Doe", "http://www.whatever.com", "address@email.com");

    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo(    // TODO ApiInfo is Deprecated
        "Dog Breed REST API's", "Some Description", "1.0",
        "urn:tos", DEFAULT_CONTACT.toString(),
        "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0");

    private static final Set<String> DEFAULT_CONSUMES =
        new HashSet<>(Arrays.asList("application/json"));

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(DEFAULT_API_INFO)
            .consumes(DEFAULT_CONSUMES);
    }
}

