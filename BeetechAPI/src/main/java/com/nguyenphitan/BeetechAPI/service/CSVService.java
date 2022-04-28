package com.nguyenphitan.BeetechAPI.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nguyenphitan.BeetechAPI.entity.Product;
import com.nguyenphitan.BeetechAPI.iofile.IOFile;
import com.nguyenphitan.BeetechAPI.repository.ProductRepository;

@Service
public class CSVService {
	@Autowired
	ProductRepository productRepository;
	
	public void save(MultipartFile file) {
		try {
			List<Product> products = IOFile.csvProducts(file.getInputStream());
			productRepository.saveAll(products);
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
}