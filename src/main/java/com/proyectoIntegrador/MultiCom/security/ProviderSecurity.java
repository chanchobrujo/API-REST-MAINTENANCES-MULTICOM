package com.proyectoIntegrador.MultiCom.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import com.proyectoIntegrador.MultiCom.model.dto.UserPrincipal;
import com.proyectoIntegrador.MultiCom.constants.TokenProperties;

import java.util.Date;
import io.jsonwebtoken.*;
import static io.jsonwebtoken.Jwts.parser;
import static io.jsonwebtoken.Jwts.builder;
import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@Component
@RequiredArgsConstructor
public class ProviderSecurity {
    private final static Logger logger = LoggerFactory.getLogger(ProviderSecurity.class);

    private final TokenProperties properties;

    public String generateToken(Authentication authentication){
        Date now = new Date();
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        return builder().setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + (this.properties.getExpiration() * 1000L)))
                .signWith(HS512, this.properties.getSecret())
                .compact();
    }

    public String getNombreUsuarioFromToken(String token){
        return parser().setSigningKey(this.properties.getSecret()).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token){
        try {
            parser().setSigningKey(this.properties.getSecret()).parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException | UnsupportedJwtException | ExpiredJwtException | IllegalArgumentException | SignatureException e){
            logger.error(e.getMessage());
        }
        return false;
    }
}