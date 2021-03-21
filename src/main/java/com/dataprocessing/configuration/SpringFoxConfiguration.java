package com.dataprocessing.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
import java.util.*;

/**
 * @author Rushin
 * @since 20-03-21
 */
@Configuration
@EnableWebMvc
@EnableSwagger2
public class SpringFoxConfiguration implements WebMvcConfigurer {

    /**
     * Swagger Docket for Schemes set & scan packages
     *
     * @param servletContext
     * @return
     */
    @Bean
    public Docket api(ServletContext servletContext) {
        Set<String> schemeSet = new HashSet<>();
        schemeSet.add("http");
        schemeSet.add("https");

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dataprocessing.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData())
                .protocols(schemeSet);
    }

    /**
     * Swagger UI Configuration for Example display, Request duration display
     *
     * @return
     */
    @Bean
    public UiConfiguration uiConfiguration() {
        return UiConfigurationBuilder.builder()
                .displayRequestDuration(true)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .build();
    }

    List<VendorExtension> vendorExtensions = new ArrayList<>();

    /**
     * Swagger API's Metadata to show in Header
     *
     * @return
     */
    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "Data Processing REST API",
                "Process data from file",
                "1.0",
                "Terms of service",
                new Contact("Rushin Vadhavana", "http://localhost:8080/", "contact@data.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0", vendorExtensions);
        return apiInfo;
    }

    /**
     * Swagger resource handler adding
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
	
}
