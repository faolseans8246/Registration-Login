package com.example.registrationsecurity.jwt;

import com.example.registrationsecurity.entity.RegistrationTable;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUnit {

    private final String SECURITY_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9xeyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQaSflKxwRJSMeKKF2QT4fwpMeJf36POk6yJVxadQssw5c";

    public String generateToken(RegistrationTable userDetails, List<String> roles) {

        Map<String, List<String>> claims = Map.of("roles", roles);

        return createToken(claims, userDetails.getUsername());
    }

    public String createToken(Map<String, List<String>> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .claim("roles", claims)
                .setExpiration(new Date(System.currentTimeMillis() + 864_000_000))
                .signWith(SignatureAlgorithm.HS256, SECURITY_KEY.getBytes())
                .compact();
    }

    public List<String> extractRoles(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.get("roles", List.class);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExprition(token).before(new Date());
    }

    private Date extractExprition(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }


}
