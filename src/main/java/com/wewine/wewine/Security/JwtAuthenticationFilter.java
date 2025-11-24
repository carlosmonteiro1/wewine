package com.wewine.wewine.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter {
    @Autowired
    private JwtTokenProvider tokenProvider;

    // Você precisará de um serviço que carregue os detalhes do usuário pelo ID/E-mail
    // Vamos simular com um UserDetails básico por enquanto, mas em um projeto real
    // você injetaria um UserDetailsService aqui.

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {

                // 1. Extrai o ID e o Perfil do token
                Long userId = tokenProvider.getUserIdFromJWT(jwt);
                String userEmail = tokenProvider.getUserEmailFromJWT(jwt);
                String userProfile = tokenProvider.getUserProfileFromJWT(jwt);

                // 2. Cria um UserDetails Simples
                // Em um sistema completo, você buscaria o usuário no banco para obter as permissões.
                UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                        userEmail,
                        "", // Senha é vazia, pois já autenticamos pelo token
                        Collections.emptyList() // Coleção de GrantedAuthorities (permissões)
                );

                // 3. Cria o objeto de Autenticação
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 4. Define a Autenticação no Contexto do Spring Security
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            // Log de erro
            logger.error("Não foi possível definir a autenticação do usuário", ex);
        }

        filterChain.doFilter(request, response);
    }

    // Método auxiliar para extrair o token do cabeçalho
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        // Verifica se o header começa com "Bearer "
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Retorna apenas o token (ignora "Bearer ")
        }
        return null;
    }
}
