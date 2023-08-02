package com.nguyenphitan.BeetechLogin.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class RegisterRequest {
	// Đánh dấu username không được trống
	@NotBlank
	private String username;
	
	// Đánh dấu password không được trống
	@NotBlank
	private String password;
}
