package com.proyectoagendador.multicom.security;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import com.proyectoagendador.multicom.repository.UserRepository;
import com.proyectoagendador.multicom.exception.BusinessException;
import com.proyectoagendador.multicom.common.constants.GeneralConstants;
import com.proyectoagendador.multicom.utils.SecurityUtil;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static com.proyectoagendador.multicom.mapper.UserMapper.buildDetailAuth;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static com.proyectoagendador.multicom.common.constants.SecurityConstants.AUTHORIZATION;

public class TokenFilterSecurity extends OncePerRequestFilter {
    @Autowired
    TokenProviderSecurity providerSecurity;

    @Autowired
    UserRepository repository;
    
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(req);
        if(this.providerSecurity.validateToken(token)){
            String email = this.providerSecurity.getValueDecrypt(token);
            UserDetails user = buildDetailAuth(this.repository.findByEmail(email).orElseThrow(() -> new BusinessException(GeneralConstants.DATA_NOT_FOUND)));
            getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
        }
        filterChain.doFilter(req, res);
    }

    private String getToken(HttpServletRequest request){
        return SecurityUtil.getToken(request.getHeader(AUTHORIZATION));
    }

}