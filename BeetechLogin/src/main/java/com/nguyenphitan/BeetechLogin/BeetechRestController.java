package com.nguyenphitan.BeetechLogin;

import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenphitan.BeetechLogin.jwt.JwtTokenProvider;
import com.nguyenphitan.BeetechLogin.payload.LoginRequest;
import com.nguyenphitan.BeetechLogin.payload.LoginResponse;
import com.nguyenphitan.BeetechLogin.payload.RandomStuff;
import com.nguyenphitan.BeetechLogin.user.CustomUserDetails;
import com.nguyenphitan.BeetechLogin.user.User;
import com.nguyenphitan.BeetechLogin.user.UserRepository;

@RestController
@RequestMapping("/api")
public class BeetechRestController {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	
	@PostMapping("/login")
	public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		// Xác thực thông tin người dùng Request lên:
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(), 
						loginRequest.getPassword()
				)
		);
		
		// Nếu không xảy ra exception tức là thông tin hợp lệ
		// Set thông tin authentication vào Security Context
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		// Trả về jwt cho người dùng
		String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
		return new LoginResponse(jwt);
	}
	
	// Đăng ký tài khoản
	@PostMapping("/sign-up")
	public User createUser(@Valid @RequestBody LoginRequest loginRequest) {
		User user = new User();
		user.setUsername(loginRequest.getUsername());
		user.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
		userRepository.save(user);
		return user;
	}
	
	// Api /api/random yêu cầu phải xác thực mới có thể request
	
	@GetMapping("/random")
	public RandomStuff randomStuff() {
		return new RandomStuff("JWT hợp lệ mới có thể nhìn thấy message này.");
	}
	
	
}
