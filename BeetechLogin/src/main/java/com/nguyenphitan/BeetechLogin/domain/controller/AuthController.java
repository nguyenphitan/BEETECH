package com.nguyenphitan.BeetechLogin.domain.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenphitan.BeetechLogin.domain.service.AuthService;
import com.nguyenphitan.BeetechLogin.request.ChangePasswordRequest;
import com.nguyenphitan.BeetechLogin.request.LoginRequest;
import com.nguyenphitan.BeetechLogin.request.RandomStuff;
import com.nguyenphitan.BeetechLogin.request.RegisterRequest;
import com.nguyenphitan.BeetechLogin.request.TokenRefreshRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	/**
	 * Check login.
	 * 
	 * @param loginRequest
	 * @return
	 */
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(authService.checkLogin(loginRequest));
	}

	/**
	 * Register new account.
	 * 
	 * @param registerRequest
	 * @return
	 */
	@PostMapping("/register")
	public ResponseEntity<?> createUser(@Valid @RequestBody RegisterRequest registerRequest) {
		return ResponseEntity.ok(authService.register(registerRequest));
	}
	
	/**
	 * Refresh token.
	 * 
	 * @param request
	 * @return
	 */
	@PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        return ResponseEntity.ok(authService.refreshToken(requestRefreshToken));
    }
	
	/**
	 * Change password.
	 * 
	 * @param changePasswordRequest
	 * @return
	 */
	@PostMapping("/change_password")
	public ResponseEntity<?> changePassword(
			@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
		
		return ResponseEntity.ok(null);
	}

	
	@GetMapping("/random")
	public RandomStuff randomStuff() {
		return new RandomStuff("JWT hợp lệ mới có thể nhìn thấy message này.");
	}

}
