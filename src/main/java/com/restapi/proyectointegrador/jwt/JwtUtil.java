package com.restapi.proyectointegrador.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "clave-secreta"; // Asegúrate de usar una clave suficientemente larga

    // Método para extraer todas las reclamaciones (claims) del token JWT
    static Claims extractAllClaims(String token) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes()); // Genera la clave

        // Usa el parser para extraer las claims
        return Jwts.parser() // Cambia a parserBuilder()
                .setSigningKey(key)
                .build() // Debes construir el parser antes de usarlo
                .parseClaimsJws(token) // Aquí se usa el JwtParser
                .getBody();
    }

    // Método para verificar la firma del token
    private static boolean isSignatureValid(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            return false;
        }

        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes()); // Genera la clave
        String signature = parts[2]; // Obtiene la firma del token

        // Recrear la firma para compararla
        String headerAndPayload = parts[0] + "." + parts[1];
        byte[] computedSignatureBytes = Keys.hmacShaKeyFor(SECRET_KEY.getBytes()).getEncoded();
        String computedSignature = Base64.getUrlEncoder().withoutPadding().encodeToString(computedSignatureBytes);

        return computedSignature.equals(signature);
    }

    // Método para verificar si el token es válido
    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token); // Intenta extraer las claims
            return isSignatureValid(token) && !isExpired(claims); // Verifica la firma y la expiración
        } catch (Exception e) {
            e.printStackTrace(); // Muestra la excepción para diagnóstico
            return false; // Si lanza excepción, el token no es válido
        }
    }

    private boolean isExpired(Claims claims) {
        // Aquí puedes implementar la lógica para verificar si el token está expirado
        Date expiration = claims.getExpiration();
        return expiration != null && expiration.before(new Date());
    }
}
