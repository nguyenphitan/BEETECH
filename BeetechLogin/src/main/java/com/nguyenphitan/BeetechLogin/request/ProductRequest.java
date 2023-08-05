package com.nguyenphitan.BeetechLogin.request;

import lombok.Data;

@Data
public class ProductRequest {
	
	private String name;
	
	private Double price;
	
	private String imgUrl;
	
	private String description;
	
	private Long categoryId;

}
