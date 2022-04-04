package com.nguyenphitan.BeetechAPI.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.nguyenphitan.BeetechAPI.model.Cart;
import com.nguyenphitan.BeetechAPI.repository.CartRepository;

@RestController
@RequestMapping("/synchronized")
public class SynchronizedController {
	
	@Autowired
	CartRepository cartRepository;
	
	/*
	 * Đồng bộ giỏ hàng sau khi đăng nhập
	 */
	@GetMapping("/cart")
	public RedirectView synchronizedCart(HttpServletRequest request) {
		HttpSession session = request.getSession();
		// Get danh sách giỏ hàng trên session lưu vào database:
		List<Cart> cartsSession = (List<Cart>) session.getAttribute("cartsSession");
		cartRepository.saveAll(cartsSession);
		return new RedirectView("/public/list-products");
	}
	
}
