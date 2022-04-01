package com.nguyenphitan.BeetechAPI.payload;

import javax.validation.constraints.NotBlank;

import com.nguyenphitan.BeetechAPI.model.Product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartResponse {
	@NotBlank
	private Long id;
	
	@NotBlank
	private Product product;
	
}
