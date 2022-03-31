package com.nguyenphitan.BeetechAPI.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.nguyenphitan.BeetechAPI.model.Product;
import com.nguyenphitan.BeetechAPI.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {
	private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
	
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping("/products")
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	@GetMapping("/products/{id}")
	public Product getProduct(@PathVariable("id") Long id) {
		return productRepository.findById(id).orElseThrow(
				() -> new UsernameNotFoundException("Product id invalid.")
		);
	}

	@PostMapping("/products")
	public RedirectView addImage(@RequestParam("image") MultipartFile multipartFile) throws IOException  {
		Path staticPath = Paths.get("static");
        Path imagePath = Paths.get("images");
        // Kiểm tra tồn tại hoặc tạo thư mục /static/images
        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }
        Path file = CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(multipartFile.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(file)) {
            os.write(multipartFile.getBytes());
        }
        
        Product product = new Product();
        product.setPhotos(imagePath.resolve(multipartFile.getOriginalFilename()).toString());
        productRepository.save(product);
        
        return new RedirectView("/list-products");
	}

	@PutMapping("/products/{id}")
	public Product updateProduct(@PathVariable("id") Long id, @Valid @RequestBody Product product) {
		return productRepository.save(product);
	}

	@DeleteMapping("/products/{id}")
	public void deleteProduct(@PathVariable("id") Long id) {
		productRepository.deleteById(id);
	}
	
}
