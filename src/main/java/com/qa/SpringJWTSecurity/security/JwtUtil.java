package com.qa.SpringJWTSecurity.security;

import com.qa.SpringJWTSecurity.dtos.user.ProfileDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.function.Function;

public class JwtUtil {
    private static final String SECRET_KEY = "cohort3_secret_key";

    public static String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractEmail(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> resolver){
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, ProfileDTO user){
        String email = extractEmail(token);
        System.out.println("EXTRACTED EMAIL #########");
        System.out.println(email);
        Boolean expiredToken = isTokenExpired(token);
        System.out.println("IS TOKEN EXPIRED #########");
        System.out.println(expiredToken);
        return email.equals(user.getEmail()) && !isTokenExpired(token);
    }
}
