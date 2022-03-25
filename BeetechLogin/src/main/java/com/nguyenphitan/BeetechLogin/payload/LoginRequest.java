package com.nguyenphitan.BeetechLogin.payload;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequest {
	// Đánh dấu username không được trống
	@NotBlank
	private String username;
	
	// Đánh dấu password không được trống
	@NotBlank
	private String password;
}
