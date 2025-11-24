package com.wewine.wewine.Security;

import com.wewine.wewine.Entity.RepresentanteEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException; // Importe explicitamente
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private int jwtExpirationMs;

    // --- MÉTODOS DE GERAÇÃO ---

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    /**
     * Gera o Token JWT contendo o e-mail, ID e perfil do representante.
     */
    public String generateToken(RepresentanteEntity representante) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(representante.getEmail()) // O e-mail é o Subject (identificador principal)
                .claim("id", representante.getId()) // ID (útil para consultas rápidas)
                .claim("perfil", representante.getTipo().name()) // Perfil do usuário (ADMIN/REPRESENTANTE)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key(), SignatureAlgorithm.HS512)
                .compact();
    }

    // --- MÉTODOS DE VALIDAÇÃO E EXTRAÇÃO ---

    /**
     * Valida se o token JWT é bem formado e se a assinatura é válida.
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            logger.error("Token JWT inválido.");
        } catch (ExpiredJwtException ex) {
            logger.error("Token JWT expirado.");
        } catch (UnsupportedJwtException ex) {
            logger.error("Token JWT não suportado.");
        } catch (IllegalArgumentException ex) {
            logger.error("A string do JWT está vazia.");
        } catch (SignatureException ex) {
            logger.error("Assinatura JWT inválida.");
        }
        return false;
    }

    /**
     * Extrai o ID do usuário (claim "id") do token.
     */
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
        return claims.get("id", Long.class);
    }

    /**
     * Extrai o e-mail do usuário (Subject) do token.
     */
    public String getUserEmailFromJWT(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    /**
     * Extrai o perfil do usuário (claim "perfil") do token.
     */
    public String getUserProfileFromJWT(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
        return claims.get("perfil", String.class);
    }
}
