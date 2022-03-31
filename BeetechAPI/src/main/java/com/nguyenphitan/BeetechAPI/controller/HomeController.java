package com.nguyenphitan.BeetechAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.repository.ProductRepository;

@Controller
public class HomeController {
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping("/")
	public ModelAndView loginPage() {
		ModelAndView modelAndView = new ModelAndView("login");
		return modelAndView;
	}
	
	@GetMapping("/admin")
	public ModelAndView adminPage() {
		ModelAndView modelAndView = new ModelAndView("admin");
		return modelAndView;
	}
	
	@GetMapping("/list-products")
	public ModelAndView productPage() {
		ModelAndView modelAndView = new ModelAndView("products");
		modelAndView.addObject("products", productRepository.findAll());
		return modelAndView;
	}
	
}
