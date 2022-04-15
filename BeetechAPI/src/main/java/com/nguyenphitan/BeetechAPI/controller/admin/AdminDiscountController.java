package com.nguyenphitan.BeetechAPI.controller.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenphitan.BeetechAPI.entity.discount.Discount;
import com.nguyenphitan.BeetechAPI.repository.discount.DiscountRepository;

/*
 * Admin thêm, sửa, xóa mã giảm giá
 * Created by: NPTAN (15/04/2022)
 * Version: 1.0
 */
@RestController
@RequestMapping("admin/api/v1/discounts")
public class AdminDiscountController {
	
	@Autowired
	DiscountRepository discountRepository;
	
	@GetMapping()
	public List<Discount> getAlls() {
		List<Discount> discounts = discountRepository.findAll();
		return discounts;
	}
	
	@GetMapping("/{id}")
	public Discount getById(@PathVariable("id") Long id) {
		Discount discount = discountRepository.getById(id);
		return discount;
	}
	
	@PostMapping()
	public Discount addDiscount(@Valid @RequestBody Discount discount) {
		discount = discountRepository.save(discount);
		return discount;
	}
	
	@PutMapping("/{id}")
	public Discount updateDiscount(@PathVariable("id") Long id, @RequestBody Discount discount) {
		discount = discountRepository.save(discount);
		return discount;
	}
	
	@DeleteMapping("/{id}") 
	public void deleteDiscount(@PathVariable("id") Long id) {
		discountRepository.deleteById(id);
	}
	
}
