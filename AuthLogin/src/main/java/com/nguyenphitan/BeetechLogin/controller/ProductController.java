package com.nguyenphitan.BeetechLogin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenphitan.BeetechLogin.model.Product;
import com.nguyenphitan.BeetechLogin.repository.ProductRepository;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/products")
public class ProductController {
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping("")
	public List<Product> products1() {
		return productRepository.findAll();
	}
	
	@PostMapping("")
	public List<Product> products2() {
		return productRepository.findAll();
	}
	
	
}
