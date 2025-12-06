package com.ursmahesh.securex.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    private final String secretKey;

    public JWTService() {
        try {
            // Use a stronger key size for better security
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            keyGen.init(256); // Specify key size explicitly
            SecretKey sk = keyGen.generateKey();
            this.secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
            // Don't log the secret key in production
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate secure JWT secret key", e);
        }
    }

    public String generateToken(String username) {
        // Default to empty type if not specified
        Map<String, Object> claims = new HashMap<>();
        claims.put("userType", "");
        return generateToken(claims, username);
    }

    public String generateToken(String username, String userType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userType", userType);
        return generateToken(claims, username);
    }

    private String generateToken(Map<String, Object> claims, String username) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(now))
                .setNotBefore(new Date(now)) // Add not before claim for additional security
                .setExpiration(new Date(now + 60 * 60 * 24 * 1000)) // Reduced to 24 hours for security
                .setId(java.util.UUID.randomUUID().toString()) // Add unique JWT ID
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractUserType(String token) {
        return extractClaim(token, claims -> claims.get("userType", String.class));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
