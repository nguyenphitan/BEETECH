package com.nguyenphitan.BeetechLogin.request;

import javax.validation.constraints.NotBlank;

import com.nguyenphitan.BeetechLogin.message.MessageUtils;

import lombok.Data;

@Data
public class TokenRefreshRequest {
	@NotBlank(message = MessageUtils.NOT_BLANK)
	private String refreshToken;
}