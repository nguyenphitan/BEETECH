package com.nguyenphitan.BeetechAPI.controller.wallet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenphitan.BeetechAPI.entity.wallet.Transaction;
import com.nguyenphitan.BeetechAPI.service.wallet.TransactionService;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
	
	@Autowired
	TransactionService transactionService;
	
	@PostMapping()
	public Transaction handleTransaction(@Valid @RequestBody Transaction transaction) throws Exception {
		return transactionService.handleTransaction(transaction);
	}
	
}
