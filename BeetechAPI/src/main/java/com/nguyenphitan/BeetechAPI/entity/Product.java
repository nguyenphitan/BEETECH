package com.nguyenphitan.BeetechAPI.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	public Product(String name, Long price, Long quantity, String photos) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.photos = photos;
	}
	
	private String name;
	
	private Long price;
	
	private Long quantity;
	
	private String photos;
}
