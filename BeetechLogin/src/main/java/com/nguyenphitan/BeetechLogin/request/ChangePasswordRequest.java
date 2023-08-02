package com.nguyenphitan.BeetechLogin.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ChangePasswordRequest {
	
	@NotNull
	private Long userId;
	
	@NotBlank
	private String password;

}
