package com.nguyenphitan.BeetechAPI.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nguyenphitan.BeetechAPI.custom.CustomUserDetails;
import com.nguyenphitan.BeetechAPI.entity.User;
import com.nguyenphitan.BeetechAPI.jwt.JwtTokenProvider;
import com.nguyenphitan.BeetechAPI.payload.LoginRequest;
import com.nguyenphitan.BeetechAPI.repository.UserRepository;

@Service
public class AuthService {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	/*
	 * Xử lý đăng nhập -> Tạo mã token
	 * Created by: NPTAN (22/04/2022)
	 * Version: 1.0
	 */
	public void handleLogin(String username, String password, HttpServletRequest request) {
		try {
			// Tạo ra LoginRequest từ username và password nhận được từ client
			LoginRequest loginRequest = new LoginRequest(username, password);
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
			// Lưu jwt vào session:
			HttpSession session = request.getSession();
			session.setAttribute("token", jwt);
			
			Long userId = tokenProvider.getUserIdFromJWT(jwt);
			User user = userRepository.getById(userId);
			session.setAttribute("role", user.getRole());
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	
	/*
	 * Xử lý đăng ký
	 * Created by: NPTAN (22/04/2022)
	 * Version: 1.0
	 */
	public User handleRegister(LoginRequest loginRequest) {
		User user = new User();
		user.setUsername(loginRequest.getUsername());
		user.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
		user.setRole("USER");
		userRepository.save(user);
		return user;
	}
	
	
}
