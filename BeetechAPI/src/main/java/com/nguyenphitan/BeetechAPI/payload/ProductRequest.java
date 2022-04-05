package com.nguyenphitan.BeetechAPI.payload;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ProductRequest {
	@NotBlank
	private Long idProduct;
	
	@NotBlank
	private Long quantitySelected;
}
