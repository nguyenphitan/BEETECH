package com.nguyenphitan.BeetechAPI.controller.wallet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenphitan.BeetechAPI.entity.wallet.UserAccount;
import com.nguyenphitan.BeetechAPI.service.wallet.UserAccountService;

@RestController
@RequestMapping("/api/v1/accounts")
public class UserAccountController {
	
	@Autowired
	UserAccountService userAccountService;
	
	@PostMapping()
	public UserAccount register(@Valid @RequestBody UserAccount userAccount) {
		return userAccountService.register(userAccount);
	}
	
}
