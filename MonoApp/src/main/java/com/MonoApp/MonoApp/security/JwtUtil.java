package com.MonoApp.MonoApp.security;


import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.*;
import java.security.Key;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtil {
private final Key key;
private final long accessExpMs;
private final long refreshExpMs;


public JwtUtil(
@Value("${jwt.secret}") String secret,
@Value("${jwt.access-token.expiration-ms}") long accessExpMs,
@Value("${jwt.refresh-token.expiration-ms}") long refreshExpMs) {
this.key = Keys.hmacShaKeyFor(secret.getBytes());
this.accessExpMs = accessExpMs; this.refreshExpMs = refreshExpMs;
}


public String generateAccessToken(String userId) {
return Jwts.builder()
.setSubject(userId)
.setIssuedAt(new Date())
.setExpiration(new Date(System.currentTimeMillis() + accessExpMs))
.signWith(key)
.compact();
}


public String generateRefreshToken(String userId) {
return Jwts.builder()
.setSubject(userId)
.setIssuedAt(new Date())
.setExpiration(new Date(System.currentTimeMillis() + refreshExpMs))
.signWith(key)
.compact();
}


public String getUserIdFromToken(String token) {
return Jwts.parserBuilder().setSigningKey(key).build()
.parseClaimsJws(token).getBody().getSubject();
}


public boolean validate(String token) {
try { Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token); return true; }
catch (JwtException ex) { return false; }
}
}