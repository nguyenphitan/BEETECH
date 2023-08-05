package com.nguyenphitan.BeetechLogin.domain.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenphitan.BeetechLogin.domain.entity.Product;
import com.nguyenphitan.BeetechLogin.domain.repository.CategoryRepository;
import com.nguyenphitan.BeetechLogin.domain.repository.ProductRepository;
import com.nguyenphitan.BeetechLogin.request.ProductRequest;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	/**
	 * Add new product.
	 * 
	 * @param productRequest
	 * @return
	 */
	@PostMapping()
	public ResponseEntity<?> addNewProduct(@RequestBody @Valid ProductRequest productRequest) {
		Product product = new Product();
		product.setCategory(categoryRepository.getById(productRequest.getCategoryId()));
		product.setDescription(productRequest.getDescription());
		product.setImgUrl(productRequest.getImgUrl());
		product.setName(productRequest.getName());
		product.setPrice(productRequest.getPrice());
		
		return ResponseEntity.ok(productRepository.save(product));	
	}
	
	/**
	 * Find all product.
	 * 
	 * @return
	 */
	@GetMapping()
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(productRepository.findAll());
	}
}
