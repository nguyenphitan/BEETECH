package com.nguyenphitan.BeetechAPI.payload;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
	// Đánh dấu username không được trống
	@NotBlank
	private String username;
	
	// Đánh dấu password không được trống
	@NotBlank
	private String password;
	
}
