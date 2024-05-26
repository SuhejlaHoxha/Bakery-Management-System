//package com.inn.cafe;
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Contact;
//import io.swagger.v3.oas.models.info.Info;
//import org.springdoc.core.models.GroupedOpenApi;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SwaggerConfig {
//
//    @Bean
//    public GroupedOpenApi publicApi() {
//        return GroupedOpenApi.builder()
//                .group("user")
//                .pathsToExclude("/v2/**")
//                .pathsToMatch("/v1/**")
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi adminApi() {
//        return GroupedOpenApi.builder()
//                .group("admin")
//                .pathsToExclude("/v1/**")
//                .pathsToMatch("/v2/**")
//                .build();
//    }
//
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .components(new Components())
//                .info(new Info()
//                        .title("Spring MVC REST API")
//                        .contact(new Contact().name("Rasika Kaluwalgoda"))
//                        .version("1.0.0"));
//    }
//}
