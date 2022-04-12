package com.nguyenphitan.BeetechAPI.service.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nguyenphitan.BeetechAPI.entity.wallet.Transaction;
import com.nguyenphitan.BeetechAPI.entity.wallet.Wallet;
import com.nguyenphitan.BeetechAPI.repository.wallet.TransactionRepository;
import com.nguyenphitan.BeetechAPI.repository.wallet.WalletRepository;

@Service
public class TransactionService {
	@Autowired
	WalletRepository walletRepository;
	
	@Autowired
	TransactionRepository transactionRepository;
	
	/*
	 * Xử lý dịch vụ nạp - thanh toán tiền
	 * Created by: NPTAN (12/04/2022)
	 */
	public Transaction handleTransaction(Transaction transaction) throws Exception {
		Long senderId = transaction.getSenderId();
		Long receiverId = transaction.getReceiverId();
		Double amount = transaction.getAmount();
		
		Wallet walletSender = walletRepository.findByUserId(senderId);
		Wallet walletReceiver = walletRepository.findByUserId(receiverId);

		Double sCurrentBalance = 0D;
		Double rCurrentBalance = 0D;
		
		if( walletSender.getBalance() != null ) {
			sCurrentBalance = walletSender.getBalance();
		}
		
		if( walletReceiver.getBalance() != null ) {
			rCurrentBalance = walletReceiver.getBalance();
		}
		
		
		// 1. Nếu senderId = receiverId -> nạp tiền
		if( senderId == receiverId ) {
			// Tăng số dư (balance):
			Double balance = sCurrentBalance + amount;
			walletSender.setBalance(balance);
			walletRepository.save(walletSender);
		}
		else {
		// 2. Nếu senderId != receiverId -> thanh toán
			// Validate:
			validateBalance(sCurrentBalance, amount);
			
			// 2.1. Tăng số dư người nhận
			Double rNewBalance = rCurrentBalance + amount;
			walletReceiver.setBalance(rNewBalance);
			walletRepository.save(walletReceiver);
			
			// 2.2. Giảm số dư người thanh toán
			Double sNewBalance = sCurrentBalance - amount;
			walletSender.setBalance(sNewBalance);
			walletRepository.save(walletSender);
		}
		
			
		return transactionRepository.save(transaction);
	}
	
	/*
	 * Kiểm tra số tiền còn lại và số tiền thanh toán:
	 * Created By: NPTAN (12/04/2022)
	 */
	public static void validateBalance(Double currentBalance, Double amount) throws Exception {
		if( currentBalance < amount ) throw new Exception("Số dư không đủ.");
	}
	
}
