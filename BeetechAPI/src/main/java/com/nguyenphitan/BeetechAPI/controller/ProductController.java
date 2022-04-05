package com.nguyenphitan.BeetechAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenphitan.BeetechAPI.entity.Product;
import com.nguyenphitan.BeetechAPI.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("public/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping()
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Product getProduct(@PathVariable("id") Long id) {
		return productRepository.findById(id).orElseThrow(
				() -> new UsernameNotFoundException("Product id invalid.")
		);
	}
	
}
