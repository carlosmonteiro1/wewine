package com.wewine.wewine.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Permitir credenciais
        config.setAllowCredentials(true);

        // Permitir todas as origens (para desenvolvimento)
        // Para produção, especifique as origens permitidas:
        // config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4200"));
        config.setAllowedOriginPatterns(Arrays.asList("*"));

        // Permitir todos os headers
        config.setAllowedHeaders(Arrays.asList("*"));

        // Permitir todos os métodos HTTP
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        // Headers expostos
        config.setExposedHeaders(Arrays.asList(
            "Authorization",
            "Content-Type",
            "Accept",
            "Origin",
            "Access-Control-Request-Method",
            "Access-Control-Request-Headers"
        ));

        // Tempo de cache da pré-verificação (em segundos)
        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

