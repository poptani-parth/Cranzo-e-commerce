package com.e_commerce.Cranzo.Config.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private  static  final  String JWT_SECRET = "f8A2xL9mP1qR7sT4uV6wX8yZ0aBcDeFgHiJkLmNoPqRsTuV";
    private  static  final SecretKey key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    public String generateToken(UserDetails userDetails){
        return  Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                //.signWith(key, SignatureAlgorithm.HS256)
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public boolean validateToken(String token, UserDetails userDetails){
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }

    private boolean isExpired(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public <T> T extractClaim(String token,
                              Function<Claims, T> claimsResolver) {

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claimsResolver.apply(claims);
    }
}
