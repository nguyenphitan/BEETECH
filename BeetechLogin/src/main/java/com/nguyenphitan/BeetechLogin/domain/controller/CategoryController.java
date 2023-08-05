package com.nguyenphitan.BeetechLogin.domain.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenphitan.BeetechLogin.domain.entity.Category;
import com.nguyenphitan.BeetechLogin.domain.repository.CategoryRepository;
import com.nguyenphitan.BeetechLogin.request.CategoryRequest;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	/**
	 * Add new category.
	 * 
	 * @param categoryRequest
	 * @return
	 */
	@PostMapping()
	public ResponseEntity<?> addNewCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
		Category newCategory = new Category();
		newCategory.setName(categoryRequest.getName());
		return ResponseEntity.ok(categoryRepository.save(newCategory));
	}
	
	/**
	 * Get all category.
	 * 
	 * @return
	 */
	@GetMapping()
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(categoryRepository.findAll());
	}

}
