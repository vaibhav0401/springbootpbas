package com.example.pbas.PBAS.jwt_config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {

	@Autowired
	private JwtConfig jwtConfig;

	public String generateToken(String userId, String roles) {
		var now = new Date();
		var expiryDate = new Date(now.getTime() + jwtConfig.getExpirationTime());

		return Jwts.builder()
				   .setSubject(userId)
				   .claim("roles", roles)
				   .setIssuedAt(now)
				   .setExpiration(expiryDate)
				   .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecretKey())
				   .compact();
	}

	public Claims validateAndParseToken(String token) {
		try {

			return Jwts.parser()
					   .setSigningKey(jwtConfig.getSecretKey())
					   .parseClaimsJws(token)
					   .getBody();
		} catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
			// Handle invalid/expired tokens
			throw new JwtException("Invalid or expired JWT token", ex);
		}
	}

	public boolean isTokenValid(String token) {
		try {
			Jwts.parser().setSigningKey(jwtConfig.getSecretKey()).parseClaimsJws(token);
			return true;
		} catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException
				 | IllegalArgumentException ex) {
			return false;
		}
	}

	public boolean isTokenExpired(String token) {
		try {
			Claims claims = validateAndParseToken(token);
			Date expirationDate = claims.getExpiration();
			return expirationDate.before(new Date());
		} catch (JwtException ex) {
			// Handle invalid/expired tokens
			return true;
		}
	}

	public String getUsernameFromToken(String token) {
		var claims = Jwts.parser().setSigningKey(jwtConfig.getSecretKey()).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
}