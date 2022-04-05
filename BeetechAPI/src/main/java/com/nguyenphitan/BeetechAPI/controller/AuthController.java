package com.nguyenphitan.BeetechAPI.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.nguyenphitan.BeetechAPI.custom.CustomUserDetails;
import com.nguyenphitan.BeetechAPI.jwt.JwtTokenProvider;
import com.nguyenphitan.BeetechAPI.payload.LoginRequest;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	/*
	 * Đăng nhập, xác thực -> lưu token vào session
	 */
	@PostMapping("/login")
	public RedirectView authenticateUser(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request) {
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
		
		return new RedirectView("/synchronized/cart");
	}
	
	/*
	 * Đăng xuất -> xóa token khỏi session
	 */
	
}
