package com.proyectoagendador.multicom.security;

import com.proyectoagendador.multicom.common.constants.SecurityConstants;
import com.proyectoagendador.multicom.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.proyectoagendador.multicom.common.properties.TokenProperties;

import java.util.Date;
import io.jsonwebtoken.*;
import static io.jsonwebtoken.Jwts.parser;
import static io.jsonwebtoken.Jwts.builder;
import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@Component
@RequiredArgsConstructor
public class TokenProviderSecurity {

    private final TokenProperties properties;

    public String generateToken(String email){
        Date now = new Date();
        return builder().setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + (this.properties.getExpiration() * 1000L)))
                .signWith(HS512, this.properties.getSecret())
                .compact();
    }

    public String getValueDecrypt(String token){
        token = token.replace(SecurityConstants.BEARER, "");
        this.validateToken(token);
        return parser().setSigningKey(this.properties.getSecret()).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token){
        try {
            parser().setSigningKey(this.properties.getSecret()).parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException | UnsupportedJwtException | ExpiredJwtException | IllegalArgumentException | SignatureException e){
            return false;
        }
    }
}