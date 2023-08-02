package com.nguyenphitan.BeetechLogin.domain.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nguyenphitan.BeetechLogin.custom.CustomUserDetails;
import com.nguyenphitan.BeetechLogin.domain.entity.AccessToken;
import com.nguyenphitan.BeetechLogin.domain.entity.RefreshToken;
import com.nguyenphitan.BeetechLogin.domain.entity.User;
import com.nguyenphitan.BeetechLogin.domain.repository.AccessTokenRepository;
import com.nguyenphitan.BeetechLogin.domain.repository.RefreshTokenRepository;
import com.nguyenphitan.BeetechLogin.domain.repository.UserRepository;
import com.nguyenphitan.BeetechLogin.domain.service.AuthService;
import com.nguyenphitan.BeetechLogin.domain.service.RefreshTokenService;
import com.nguyenphitan.BeetechLogin.exception.TokenRefreshException;
import com.nguyenphitan.BeetechLogin.jwt.JwtTokenProvider;
import com.nguyenphitan.BeetechLogin.request.ChangePasswordRequest;
import com.nguyenphitan.BeetechLogin.request.LoginRequest;
import com.nguyenphitan.BeetechLogin.request.RegisterRequest;
import com.nguyenphitan.BeetechLogin.response.LoginResponse;
import com.nguyenphitan.BeetechLogin.response.TokenRefreshResponse;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	RefreshTokenService refreshTokenService;
	
	@Autowired
	AccessTokenRepository accessTokenRepository;
	
	@Autowired
	RefreshTokenRepository refreshTokenRepository;

	/**
	 * Check login.
	 * 
	 * @param loginRequest
	 * @return
	 */
	@Override
	public LoginResponse checkLogin(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(), 
						loginRequest.getPassword()
				)
		);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
		Long userId = tokenProvider.getUserIdFromJWT(jwt);
		
		AccessToken accessToken = new AccessToken();
		accessToken.setToken(jwt);
		accessToken.setUserId(userId);
		accessTokenRepository.save(accessToken);
		
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userId);
		
		return new LoginResponse(jwt, refreshToken.getToken());
	}

	/**
	 * Register new account.
	 * 
	 * @param registerRequest
	 * @return
	 */
	@Override
	public User register(RegisterRequest registerRequest) {
		User user = new User();
		user.setUsername(registerRequest.getUsername());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		userRepository.save(user);
		return user;
	}

	/**
	 * Refresh token.
	 * 
	 * @param token
	 * @return
	 */
	@Override
	public TokenRefreshResponse refreshToken(String refreshToken) {
		RefreshToken tokenRefresh = refreshTokenRepository.findByToken(refreshToken).get();
		if(!tokenRefresh.getIsActive()) {
			throw new TokenRefreshException(refreshToken, "Refresh token is not active");
		}
		
		return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserId)
                .map(userId -> {
                	User user = userRepository.findById(userId).get();
                    String accessToken = tokenProvider.generateToken(new CustomUserDetails(user));
                    return new TokenRefreshResponse(accessToken, refreshToken);
                })
                .orElseThrow(() -> new TokenRefreshException(refreshToken,
                        "Refresh token is not in database!"));
	}

	/**
	 * Change password.
	 * 
	 * @param changePasswordRequest
	 * @return
	 */
	@Override
	public int changePassword(ChangePasswordRequest changePasswordRequest) {
		try {
			User user = userRepository.findById(changePasswordRequest.getUserId()).get();
			user.setPassword(passwordEncoder.encode(changePasswordRequest.getPassword()));
			userRepository.save(user);
			
			updateAccessToken(user);
			updateRefreshToken(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	/**
	 * Update isActive value of AccessToken.
	 * 
	 * @param user
	 */
	private void updateAccessToken(User user) {
		List<AccessToken> accessTokens = accessTokenRepository.findAll()
				.stream().filter(accessToken -> accessToken.getUserId() == user.getId())
				.collect(Collectors.toList());
		accessTokens.stream().forEach(item -> item.setIsActive(false));
		accessTokenRepository.saveAll(accessTokens);
	}
	
	/**
	 * Update isActive value of RefreshToken.
	 * 
	 * @param user
	 */
	private void updateRefreshToken(User user) {
		List<RefreshToken> refreshTokens = refreshTokenRepository.findAll()
				.stream().filter(refreshToken -> refreshToken.getUserId() == user.getId())
				.collect(Collectors.toList());
		refreshTokens.stream().forEach(item -> item.setIsActive(false));
		refreshTokenRepository.saveAll(refreshTokens);
	}

}
