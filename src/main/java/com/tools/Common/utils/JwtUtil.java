package com.tools.Common.utils;

import com.tools.Common.exception.PacketException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JwtUtil {
    private final SecretKey secretKey;

    public JwtUtil() {
        this.secretKey = Keys.hmacShaKeyFor("".getBytes());
    }

    public String validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            throw new PacketException("인증 실패!");
        }
    }
}
