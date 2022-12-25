package com.proyectoagendador.multicom.security;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.proyectoagendador.multicom.utils.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public
class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public
    void handle (HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException {
        response.setContentType(SecurityUtil.contentType);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(SecurityUtil.retrieveData(e.getMessage()));
        response.getWriter().close();
    }
}
