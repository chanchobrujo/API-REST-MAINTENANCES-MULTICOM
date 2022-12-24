package com.proyectoIntegrador.MultiCom.security;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.proyectoIntegrador.MultiCom.service.user.UserDetailPrincipalService;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

public class TokenFilterSecurity extends OncePerRequestFilter {
    @Autowired
    TokenProviderSecurity providerSecurity;

    @Autowired
    UserDetailPrincipalService service;
    
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(req);
        if(token != null && this.providerSecurity.validateToken(token)){
            String email = this.providerSecurity.getValueDecrypt(token);
            UserDetails user = this.service.loadUserByUsername(email);
            getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
        }
        filterChain.doFilter(req, res);
    }
    
    private String getToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer")) {
            return header.replace("Bearer ", "");
        }
        return null;
    }

}