package com.nguyenphitan.BeetechAPI.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.nguyenphitan.BeetechAPI.entity.User;
import com.nguyenphitan.BeetechAPI.payload.LoginRequest;
import com.nguyenphitan.BeetechAPI.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
	
	@Autowired
	AuthService authService;
	
	
	
	/*
	 * Đăng nhập, xác thực:
	 */
	@PostMapping("/login")
	public RedirectView authenticateUser(
		@RequestParam("username") String username, 
		@RequestParam("password") String password, 
		HttpServletRequest request
	) throws Exception {
		authService.handleLogin(username, password, request);
		return new RedirectView("/synchronized/cart");
	}
	
	/*
	 * Đăng ký tài khoản
	 */
	@PostMapping("/register")
	public User createUser(@Valid @RequestBody LoginRequest loginRequest) {
		return authService.handleRegister(loginRequest);
	}
	
	/*
	 * Đăng xuất -> xóa token khỏi session
	 */
	@GetMapping("/logout")
	public RedirectView logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		
		// Xóa token trên session:
		session.removeAttribute("token");
		
		// Xóa role:
		session.removeAttribute("role");
		
		// Xóa cookie:
		for (Cookie cookie : request.getCookies()) {
		    cookie.setValue("");
		    cookie.setMaxAge(0);
		    cookie.setPath("/");

		    response.addCookie(cookie);
		}
		
		// Chuyển về trang đăng nhập:
		
		return new RedirectView("/");
	}
	
	
}
