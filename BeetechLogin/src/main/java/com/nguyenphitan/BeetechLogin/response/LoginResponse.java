package com.nguyenphitan.BeetechLogin.response;

import lombok.Data;

@Data
public class LoginResponse {
	private String accessToken;
	private String refreshToken;
	private String tokenType = "Bearer";
	
	public LoginResponse(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
