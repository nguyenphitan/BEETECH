package com.nguyenphitan.BeetechAPI.service.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nguyenphitan.BeetechAPI.entity.wallet.UserAccount;
import com.nguyenphitan.BeetechAPI.entity.wallet.Wallet;
import com.nguyenphitan.BeetechAPI.repository.wallet.UserAccountRepository;
import com.nguyenphitan.BeetechAPI.repository.wallet.WalletRepository;

@Service
public class UserAccountService {
	
	@Autowired
	UserAccountRepository userAccountRepository;
	
	@Autowired
	WalletRepository walletRepository;
	
	/*
	 * Đăng ký tài khoản
	 * Create by: NPTAN (12/04/2022)
	 */
	public UserAccount register(UserAccount userAccount) {
		// validate dữ liệu
		// 1. Kiểm tra email có hợp lệ hay không, email không được trùng.
		// 2. Kiểm tra số điện thoại có đúng định dạng hay không?
		// ...
		
		
		// Lưu tài khoản vào database:
		userAccount = userAccountRepository.save(userAccount);
		
		// Tạo ví:
		Long userId = userAccount.getId();
		Wallet wallet = new Wallet();
		wallet.setUserId(userId);
		wallet.setBalance(0D);
		walletRepository.save(wallet);
		
		return userAccount;
	}
	
}
