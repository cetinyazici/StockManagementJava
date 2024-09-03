package com.stockmanagement.StockManagement.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.key}")
    private String SECRET;

    // Token oluşturma
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("Cet", "Yaz");
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 2)) // 2 dakika geçerlilik süresi
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // Sign key ve algoritma
                .compact();
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUser(token);
        Date expirationDate = extractExpiration(token);
        return userDetails.getUsername() == username && expirationDate.after(new Date());
    }

    private Date extractExpiration(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
        return claims.getExpiration();
    }

    public String extractUser(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
        return claims.getSubject();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
