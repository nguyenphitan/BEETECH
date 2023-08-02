package com.nguyenphitan.BeetechLogin.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nguyenphitan.BeetechLogin.custom.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {

	@Value("${jwt.app.jwtSecret}")
	private String jwtSecret;

	@Value("${jwt.app.jwtAccessExpiration}")
	private long jwtExpiration;

	/**
	 * Generate token from user id.
	 * 
	 * @param userDetails
	 * @return
	 */
	public String generateToken(CustomUserDetails userDetails) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpiration);
		
		return Jwts.builder()
					.setSubject(Long.toString(userDetails.getUser().getId()))
					.setIssuedAt(now)
					.setExpiration(expiryDate)
					.signWith(SignatureAlgorithm.HS512, jwtSecret)
					.compact();
	}
	
	/**
	 * Get user id from token.
	 * 
	 * @param token
	 * @return
	 */
	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser()
							.setSigningKey(jwtSecret)
							.parseClaimsJws(token)
							.getBody();
		
		return Long.parseLong(claims.getSubject());
	}
	
	/**
	 * Validate token.
	 * 
	 * @param authToken
	 * @return
	 */
	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty.");
		} catch (ExpiredJwtException e) {
			log.error("Expired JWT token.");
			throw new ExpiredJwtException(null, null, "Expired JWT token");
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token.");
		} catch (UnsupportedJwtException e) {
			log.error("Unsupported JWT token.");
		}
		return false;
	}

}
