package com.restapi.proyectointegrador.jwt;

import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.jsonwebtoken.Jwts.*;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final String secret;

    public JwtRequestFilter(@Value("${security.jwt.secret.key}") String secret) {
        this.secret = secret;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (request.getRequestURI().equals("/api/v1/auth")
                || request.getRequestURI().equals("/health")
                || request.getRequestURI().startsWith("/swagger")
                || request.getRequestURI().startsWith("/v3/api-docs")) {
            filterChain.doFilter(request, response);
        } else if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
        } else {
            try {
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), "Missing or wrong token");
                    return;
                }

                String token = authHeader.substring(7);

                // Aquí puedes usar el método de JwtUtil para validar el token
                Claims claimsBody = JwtUtil.extractAllClaims(token); // Asumiendo que tienes una instancia de JwtUtil

                String subject = claimsBody.getSubject();
                List<String> roles = claimsBody.get("ada_roles", ArrayList.class);

                if (roles == null) {
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), "Missing or wrong token");
                    return;
                }

                AuthenticationController authentication = new AuthenticationController(token, subject, roles);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                request.setAttribute("claims", claimsBody);
                request.setAttribute("jwtUserId", subject);
                request.setAttribute("jwtUserRoles", roles);
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (MalformedJwtException e) {
                response.sendError(HttpStatus.BAD_REQUEST.value(), "Missing or wrong token");
            } catch (ExpiredJwtException | SignatureException e) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token expired or malformed");
            }

            filterChain.doFilter(request, response);
        }
    }

}