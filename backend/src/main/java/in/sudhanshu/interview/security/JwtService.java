package in.sudhanshu.interview.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import in.sudhanshu.interview.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId())
                .claim("role", user.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey()).compact();
    }

    public String extractEmail(String token) {
        return extractToken(token).getSubject();
    }

    public Long extractUserId(String token) {
        return extractToken(token).get("userId", Long.class);
    }

    public String extractRole(String token) {
        return extractToken(token).get("role", String.class);
    }

    public boolean isTokenValid(String token) {
        try {
            extractToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
