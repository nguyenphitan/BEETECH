package com.nguyenphitan.BeetechLogin.domain.service.impl;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nguyenphitan.BeetechLogin.domain.entity.RefreshToken;
import com.nguyenphitan.BeetechLogin.domain.repository.RefreshTokenRepository;
import com.nguyenphitan.BeetechLogin.domain.repository.UserRepository;
import com.nguyenphitan.BeetechLogin.domain.service.RefreshTokenService;
import com.nguyenphitan.BeetechLogin.exception.TokenRefreshException;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

	@Value("${jwt.app.jwtRefreshExpirationMs}")
	private Long refreshTokenDurationMs;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * Find by token.
	 * 
	 * @param token
	 * @return
	 */
	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	/**
	 * Create refresh token.
	 * 
	 * @param userId
	 * @return
	 */
	public RefreshToken createRefreshToken(Long userId) {
		RefreshToken refreshToken = new RefreshToken();

		refreshToken.setUserId(userRepository.findById(userId).get().getId());
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
		refreshToken.setToken(UUID.randomUUID().toString());

		refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}

	/**
	 * Verify expiration.
	 * 
	 * @param token
	 * @return
	 */
	public RefreshToken verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(token);
			throw new TokenRefreshException(token.getToken(),
					"Refresh token was expired. Please make a new signin request");
		}

		return token;
	}

	@Transactional
	public int deleteByUserId(Long userId) {
		return refreshTokenRepository.deleteByUserId(userId);
	}

}
