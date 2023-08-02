package com.nguyenphitan.BeetechLogin.domain.service;

import java.util.Optional;

import com.nguyenphitan.BeetechLogin.domain.entity.RefreshToken;

public interface RefreshTokenService {
	/**
	 * Find by token.
	 * 
	 * @param token
	 * @return
	 */
	Optional<RefreshToken> findByToken(String token);
	
	/**
	 * Create refresh token.
	 * 
	 * @param userId
	 * @return
	 */
	RefreshToken createRefreshToken(Long userId);
	
	/**
	 * Verify expiration.
	 * 
	 * @param token
	 * @return
	 */
	RefreshToken verifyExpiration(RefreshToken token);
	
	int deleteByUserId(Long userId);
}
