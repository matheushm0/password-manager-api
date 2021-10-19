package br.mhm.passwordmanagerapi.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint
{

/* --------------------------------------------------------------------------------------------- */

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException
    {
        response.setContentType("application/json");

        if (authException instanceof InsufficientAuthenticationException) {
            response.setStatus(401);
        }
        else {
            response.setStatus(403);
        }

        response.getWriter().print("{\"error\":\"Invalid credentials\"}");
    }

/* --------------------------------------------------------------------------------------------- */

}
