package com.nguyenphitan.BeetechAPI.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.model.Cart;
import com.nguyenphitan.BeetechAPI.model.Product;
import com.nguyenphitan.BeetechAPI.payload.CartResponse;
import com.nguyenphitan.BeetechAPI.repository.CartRepository;
import com.nguyenphitan.BeetechAPI.repository.ProductRepository;

@Controller
public class HomeController {
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CartRepository cartRepository;
	
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
	
	@GetMapping("/public/list-products")
	public ModelAndView productPage(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("products");
		modelAndView.addObject("products", productRepository.findAll());
		
		// Kiểm tra token:
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute("token");
		// Khi chưa đăng nhập: (chưa có token)
		if(token == null) {
			modelAndView.addObject("totalQuantity", 0);
		}
		else {
			// Load số lượng sản phẩm trong giỏ hàng
			List<Cart> listProductInCarts = cartRepository.findAll();
			int totalQuantity = listProductInCarts.size();
			modelAndView.addObject("totalQuantity", totalQuantity);			
		}

		return modelAndView;
	}
	
	@GetMapping("/public/details")
	public ModelAndView detailPage(@RequestParam("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("details");
		modelAndView.addObject("product", (Object) productRepository.findById(id));
		return modelAndView;
	}
	
	@GetMapping("/list-cart")
	public ModelAndView cartPage() {
		ModelAndView modelAndView = new ModelAndView("carts");
		List<CartResponse> listProducts = new ArrayList<CartResponse>();
		List<Cart> listCarts = cartRepository.findAll();
		for(Cart cart: listCarts) {
			Long idProductInCart = cart.getId();
			Long idProduct = cart.getId_product();
			Product product = productRepository.getById(idProduct);
			CartResponse cartResponse = new CartResponse(idProductInCart, product);
			listProducts.add(cartResponse);
		}
		modelAndView.addObject("listProducts", listProducts);
		return modelAndView;
	}
	
}
