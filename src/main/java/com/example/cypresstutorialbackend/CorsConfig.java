package com.example.cypresstutorialbackend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Erlaube CORS für alle API-Endpunkte
                        .allowedOrigins("http://localhost:5173") // Erlaube Anfragen vom Frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Erlaube diese HTTP-Methoden
                        .allowedHeaders("*") // Erlaube alle Header
                        .allowCredentials(true); // Erlaube Cookies/Authentifizierungsdaten
            }
        };
    }
}


