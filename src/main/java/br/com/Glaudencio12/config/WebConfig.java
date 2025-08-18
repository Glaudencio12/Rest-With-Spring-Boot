package br.com.Glaudencio12.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //Configura cors globalmente
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080", "https://meusite.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        /** Content Negotiation por Query Params
         configurer.favorParameter(true).parameterName("mediaType").ignoreAcceptHeader(true)
                 .useRegisteredExtensionsOnly(false)
                 .defaultContentType(MediaType.APPLICATION_JSON)
                 .mediaType("json", MediaType.APPLICATION_JSON)
                 .mediaType("xml", MediaType.APPLICATION_XML);
         */

        //Negotiation por Header Params
        configurer.favorParameter(false).ignoreAcceptHeader(false).useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("yml", MediaType.APPLICATION_YAML);
    }
}
