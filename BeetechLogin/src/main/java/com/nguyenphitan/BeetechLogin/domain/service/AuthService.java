package com.nguyenphitan.BeetechLogin.domain.service;

import com.nguyenphitan.BeetechLogin.domain.entity.User;
import com.nguyenphitan.BeetechLogin.request.ChangePasswordRequest;
import com.nguyenphitan.BeetechLogin.request.LoginRequest;
import com.nguyenphitan.BeetechLogin.request.RegisterRequest;
import com.nguyenphitan.BeetechLogin.response.LoginResponse;
import com.nguyenphitan.BeetechLogin.response.TokenRefreshResponse;

public interface AuthService {
	
	/**
	 * Check login.
	 * 
	 * @param loginRequest
	 * @return
	 */
	LoginResponse checkLogin(LoginRequest loginRequest);
	
	/**
	 * Register new account.
	 * 
	 * @param registerRequest
	 * @return
	 */
	User register(RegisterRequest registerRequest);
	
	/**
	 * Refresh token.
	 * 
	 * @param refreshToken
	 * @return
	 * @throws Exception 
	 */
	TokenRefreshResponse refreshToken(String refreshToken) throws Exception;
	
	/**
	 * Change password.
	 * 
	 * @param changePasswordRequest
	 * @return
	 */
	int changePassword(ChangePasswordRequest changePasswordRequest);

}
