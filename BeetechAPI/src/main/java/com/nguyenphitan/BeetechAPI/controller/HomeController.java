package com.nguyenphitan.BeetechAPI.controller;

import java.util.HashMap;
import java.util.Map;

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
	
	@GetMapping("/add-list")
	public ModelAndView addListProduct() {
		ModelAndView modelAndView = new ModelAndView("add_list_product");
		return modelAndView;
	}
	
	/*
	 * Danh sách sản phẩm
	 */
	@GetMapping("/list-products")
	public ModelAndView productPage(HttpServletRequest request) {
		ModelAndView modelAndView = productService.getAllProduct("products", request);
		return modelAndView;
	}
	
	/*
	 * Chi tiết sản phẩm
	 */
	@GetMapping("/details")
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
	 * Giao diện thanh toán
	 */
	@GetMapping("/payment")
	public ModelAndView payment() {
		ModelAndView modelAndView = new ModelAndView("index");
		return modelAndView;
	}
	
	/*
	 * Trả về thông tin thanh toán cho khách hàng
	 */
	@GetMapping("/vnpay_return")
	public ModelAndView returnPage(
	
			@RequestParam("vnp_Amount") String amount,
			@RequestParam("vnp_BankCode") String bankCode,
			@RequestParam("vnp_BankTranNo") String bankTranNo,
			@RequestParam("vnp_CardType") String cardType,
			@RequestParam("vnp_OrderInfo") String orderInfo,
			@RequestParam("vnp_PayDate") String payDate,
			@RequestParam("vnp_ResponseCode") String responseCode,
			@RequestParam("vnp_TmnCode") String tmnCode,
			@RequestParam("vnp_TransactionNo") String transactionNo,
			@RequestParam("vnp_TransactionStatus") String transactionStatus,
			@RequestParam("vnp_TxnRef") String txnRef,
			@RequestParam("vnp_SecureHash") String secureHash
			
	) {
		ModelAndView modelAndView = new ModelAndView("vnpay_return");
		
		Map<String, String> data = new HashMap<String, String>();
		data.put("amount", amount);
		data.put("bankCode", bankCode);
		data.put("bankTranNo", bankTranNo);
		data.put("cardType", cardType);
		data.put("orderInfo", orderInfo);
		data.put("payDate", payDate);
		data.put("responseCode", responseCode);
		data.put("tmnCode", tmnCode);
		data.put("transactionNo", transactionNo);
		data.put("transactionStatus", transactionStatus);
		data.put("txnRef", txnRef);
		data.put("secureHash", secureHash);
		
		modelAndView.addObject("data", data);
		
		return modelAndView;
	}
}
