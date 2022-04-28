package com.nguyenphitan.BeetechAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.nguyenphitan.BeetechAPI.entity.Product;
import com.nguyenphitan.BeetechAPI.iofile.IOFile;
import com.nguyenphitan.BeetechAPI.message.ResponseMessage;
import com.nguyenphitan.BeetechAPI.service.CSVService;


@CrossOrigin("http://localhost:8080")
@Controller
@RequestMapping("/api/csv")
public class CSVController {
	@Autowired
	CSVService fileService;

	@PostMapping("/upload")
	public RedirectView uploadFile(Model model, @RequestParam("file") MultipartFile file) {
		String message = "";
		if (IOFile.hasCSVFormat(file)) {
			try {
				fileService.save(file);
				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				return new RedirectView("/list-products");
			} catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				model.addAttribute("message", message);
				return new RedirectView("/add-list");
			}
		}
		message = "Please upload a csv file!";
		return new RedirectView("/list-products");
	}

	@GetMapping("/tutorials")
	public ResponseEntity<List<Product>> getAllProducts() {
		try {
			List<Product> products = fileService.getAllProducts();
			if (products.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(products, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}