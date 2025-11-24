package com.wewine.wewine.Security;

import com.wewine.wewine.Entity.RepresentanteEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private int jwtExpirationMs;

    // 1. GERAÇÃO DO TOKEN
    public String generateToken(RepresentanteEntity representante) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        // A chave de assinatura é gerada a partir do segredo no application.properties
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));

        return Jwts.builder()
                .setSubject(representante.getEmail()) // Identificador único: o e-mail
                .claim("id", representante.getId()) // Adiciona o ID como um 'claim' (informação) no token
                .claim("perfil", representante.getTipo().name()) // Adiciona o TipoUsuario/Perfil
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
}
