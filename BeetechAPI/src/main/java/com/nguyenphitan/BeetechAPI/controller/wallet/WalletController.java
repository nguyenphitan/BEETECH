package com.nguyenphitan.BeetechAPI.controller.wallet;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenphitan.BeetechAPI.entity.wallet.Wallet;
import com.nguyenphitan.BeetechAPI.repository.wallet.WalletRepository;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {
	@Autowired
	WalletRepository walletRepository;
	
	@GetMapping()
	public List<Wallet> getAllWallets() {
		List<Wallet> wallets = walletRepository.findAll();
		return wallets;
	}
	
	@GetMapping("/{id}")
	public Wallet getWalletById(@PathVariable("id") Long id) {
		return walletRepository.findById(id).orElseThrow(
				() -> new UsernameNotFoundException("Wallet id invalid.")
		);
	}
	
	@PostMapping()
	public Wallet createWallet(@Valid @RequestBody Wallet wallet) {
		return walletRepository.save(wallet);
	}
	
	@PutMapping("/{id}")
	public Wallet updateWallet(@PathVariable("id") Long id, @Valid @RequestBody Wallet wallet) {
		return walletRepository.save(wallet);
	}

//	@DeleteMapping("/{id}")
//	public void deleteUser(@PathVariable("id") Long id) {
//		walletRepository.deleteById(id);
//	}
	
	
}
