package com.nguyenphitan.BeetechAPI.repository.wallet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nguyenphitan.BeetechAPI.entity.wallet.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	List<Transaction> findBySenderId(Long senderId);
	
	List<Transaction> findByReceiverId(Long receiverId);
}
