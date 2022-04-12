package com.nguyenphitan.BeetechAPI.repository.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nguyenphitan.BeetechAPI.entity.wallet.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
	UserAccount findByEmail(String email);
	
	UserAccount findByPhoneNumber(String phoneNumber);
}
