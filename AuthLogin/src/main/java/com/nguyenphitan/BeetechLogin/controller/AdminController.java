package com.nguyenphitan.BeetechLogin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.nguyenphitan.BeetechLogin.model.Product;
import com.nguyenphitan.BeetechLogin.repository.ProductRepository;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminController {
	private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
	
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping()
	public ModelAndView admin() {
		ModelAndView modelAndView = new ModelAndView("AddImages");
		return modelAndView;
	}
	
	@PostMapping("/add-image")
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
        
        return new RedirectView("/products");
	}
	
	
}
