package com.motohive.userservice.security;

import com.motohive.userservice.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtService {
	
	@Value("${motohive.jwt.secret}")
	private String secret;
	
	@Value("${motohive.jwt.access-token-expiry-ms}")
	private long accessTokenExpiryMs;
	
	@Value("${motohive.jwt.refresh-token-expiry-ms}")
	private long refreshTokenExpiryMs;
	
	// ── Key ───────────────────────────────────────────
	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}
	
	// ── Generate Access Token ─────────────────────────
	public String generateAccessToken(Long userId, String email, Set<Role> roles) {
		return Jwts.builder()
				       .subject(email)
				       .claim("userId", userId)
				       .claim("roles", roles.stream()
						                       .map(Role::name)
						                       .collect(Collectors.toList()))
				       .issuedAt(new Date())
				       .expiration(new Date(System.currentTimeMillis() + accessTokenExpiryMs))
				       .signWith(getSigningKey())
				       .compact();
	}
	
	// ── Generate Refresh Token ────────────────────────
	public String generateRefreshToken() {
		// refresh token is a random UUID — not a JWT
		return UUID.randomUUID().toString();
	}
	
	public long getRefreshTokenExpiryMs() {
		return refreshTokenExpiryMs;
	}
	
	// ── Validate & Parse ──────────────────────────────
	public Claims extractAllClaims(String token) {
		return Jwts.parser()
				       .verifyWith(getSigningKey())
				       .build()
				       .parseSignedClaims(token)
				       .getPayload();
	}
	
	public String extractEmail(String token) {
		return extractAllClaims(token).getSubject();
	}
	
	public Long extractUserId(String token) {
		return extractAllClaims(token).get("userId", Long.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> extractRoles(String token) {
		return extractAllClaims(token).get("roles", List.class);
	}
	
	public boolean isTokenValid(String token) {
		try {
			extractAllClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}