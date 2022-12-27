package com.proyectoagendador.multicom.security;

import java.io.*;

import com.proyectoagendador.multicom.utils.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Component
public class TokenEntryPointSecurity implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse response, AuthenticationException e) throws IOException {
        response.setContentType(SecurityUtil.contentType);
        response.setStatus(SC_UNAUTHORIZED);
        response.getWriter().write(SecurityUtil.retrieveData(e.getMessage()));
        response.getWriter().close();
    }
} 