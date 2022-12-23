package com.proyectoIntegrador.MultiCom.security;

import com.proyectoIntegrador.MultiCom.service.user.UserDetailsServiceImpl;
import org.slf4j.*; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*; 
import javax.servlet.http.*; 
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Autowired
    ProviderSecurity providerSecurity;

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getToken(req);
            if(token != null && providerSecurity.validateToken(token)){
                String email = providerSecurity.getNombreUsuarioFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e){
            logger.error("fail en el método doFilter." + e.getMessage());
        }
        filterChain.doFilter(req, res);
    }
    
    private String getToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if( header != null && header.startsWith("Bearer") )
            return header.replace("Bearer ", "");
        return null;
    }

}