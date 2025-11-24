package com.wewine.wewine.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Indica que esta classe contém métodos de configuração de Beans
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Define e expõe o BCryptPasswordEncoder como um Bean gerenciado pelo Spring.
     * O RepresentanteService pode agora injetar PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Desabilita a proteção CSRF, que geralmente não é necessária
                // em APIs REST que usam tokens ou autenticação sem estado.
                .csrf(AbstractHttpConfigurer::disable)

                // Define as regras de autorização para as requisições HTTP
                .authorizeHttpRequests(authorize -> authorize
                        // Permite o acesso público ao endpoint de LOGIN
                        .requestMatchers(HttpMethod.POST, "/api/representantes/login").permitAll()

                        // Permite o acesso público ao endpoint de CADASTRO (ex: para o Admin)
                        .requestMatchers(HttpMethod.POST, "/api/representantes").permitAll()

                        // Qualquer outra requisição, em qualquer outra rota, deve ser AUTENTICADA.
                        // Isso protege /api/pedidos, /api/clientes, /api/vinhos e outras consultas administrativas.
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}