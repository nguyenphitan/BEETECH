package com.nguyenphitan.BeetechAPI.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.jwt.JwtTokenProvider;
import com.nguyenphitan.BeetechAPI.repository.CartRepository;
import com.nguyenphitan.BeetechAPI.repository.ProductRepository;
import com.nguyenphitan.BeetechAPI.repository.UserRepository;
import com.nguyenphitan.BeetechAPI.repository.discount.DiscountRepository;
import com.nguyenphitan.BeetechAPI.repository.wallet.UserAccountRepository;
import com.nguyenphitan.BeetechAPI.service.CartService;
import com.nguyenphitan.BeetechAPI.service.ProductService;

@Controller
public class HomeController {
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired 
	UserRepository userRepository;
	
	@Autowired
	UserAccountRepository userAccountRepository;
	
	@Autowired
	DiscountRepository discountRepository;
	
	@Autowired
	CartService cartService;
	
	@Autowired
	ProductService productService;
	
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
	
	/*
	 * Danh sách sản phẩm
	 */
	@GetMapping("/public/list-products")
	public ModelAndView productPage(HttpServletRequest request) {
		ModelAndView modelAndView = productService.getAllProduct("products", request);
		return modelAndView;
	}
	
	/*
	 * Chi tiết sản phẩm
	 */
	@GetMapping("/public/details")
	public ModelAndView detailPage(@RequestParam("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("details");
		modelAndView.addObject("product", (Object) productRepository.findById(id));
		return modelAndView;
	}
	
	/*
	 * Danh sách giỏ hàng
	 */
	@GetMapping("/list-cart")
	public ModelAndView cartPage(HttpServletRequest request) {
		ModelAndView modelAndView = cartService.getAllCart("carts", request);
		return modelAndView;
	}
	
	/*
	 * Giao diện hóa đơn
	 */
	@GetMapping("/bill/**")
	public ModelAndView billPage(HttpServletRequest request) {
		ModelAndView modelAndView = cartService.getAllCart("bill", request);
		return modelAndView;
	}
	
	/*
	 * Giao diện đăng ký ví điện tử
	 */
	@GetMapping("/wallets/register")
	public ModelAndView registerWallet() {
		ModelAndView modelAndView = new ModelAndView("registerWallet");
		return modelAndView;
	}
	
	
}
