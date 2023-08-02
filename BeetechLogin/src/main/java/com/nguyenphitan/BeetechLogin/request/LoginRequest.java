package com.nguyenphitan.BeetechLogin.request;

import javax.validation.constraints.NotBlank;

import com.nguyenphitan.BeetechLogin.message.MessageUtils;

import lombok.Data;

@Data
public class LoginRequest {

	@NotBlank(message = MessageUtils.NOT_BLANK)
	private String username;

	@NotBlank(message = MessageUtils.NOT_BLANK)
	private String password;
}
