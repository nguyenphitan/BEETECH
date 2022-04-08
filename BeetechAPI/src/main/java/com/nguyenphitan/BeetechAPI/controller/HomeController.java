package com.nguyenphitan.BeetechAPI.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.entity.Cart;
import com.nguyenphitan.BeetechAPI.entity.Product;
import com.nguyenphitan.BeetechAPI.entity.User;
import com.nguyenphitan.BeetechAPI.jwt.JwtTokenProvider;
import com.nguyenphitan.BeetechAPI.payload.CartResponse;
import com.nguyenphitan.BeetechAPI.repository.CartRepository;
import com.nguyenphitan.BeetechAPI.repository.ProductRepository;
import com.nguyenphitan.BeetechAPI.repository.UserRepository;

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
		
		// Lấy user id user từ mã token:
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute("token");
		

		// Khi chưa đăng nhập: (chưa có token)
		// Load số lượng sản phẩm trên session:
		if(token == null) {
			List<Cart> cartsSession = (List<Cart>) session.getAttribute("cartsSession");
			int totalQuantity = 0;
			if(cartsSession != null) {
				totalQuantity = cartsSession.size();
			}
			modelAndView.addObject("totalQuantity", totalQuantity);
		}
		else {
		// Khi đã đăng nhập
		// Load số lượng sản phẩm trong giỏ hàng ứng với từng idUser:
			Long idUser = jwtTokenProvider.getUserIdFromJWT(token);
			List<Cart> listProductInCarts = cartRepository.findByIdUser(idUser);
			if( listProductInCarts != null ) {
				int totalQuantity = listProductInCarts.size();
				modelAndView.addObject("totalQuantity", totalQuantity);							
			}
			else {
				modelAndView.addObject("totalQuantity", 0);
			}
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
	public ModelAndView cartPage(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("carts");
		
		// Lấy user id user từ mã token:
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute("token");
		
		List<CartResponse> listProducts = new ArrayList<CartResponse>();
		
		// Nếu chưa có token -> get list cart từ session
		if(token == null) {
			List<Cart> cartsSession = (List<Cart>) session.getAttribute("cartsSession");
			// Nếu chưa có sản phẩm -> hiển thị chưa có sản phẩm nào:
			if( cartsSession == null ) {
				modelAndView.addObject("listProducts", null);
				return modelAndView;
			}
			// Nếu có sản phẩm -> hiển thị danh sách sản phẩm:
			for(Cart cart: cartsSession) {
				Long idCart = cart.getId();
				Long idProduct = cart.getIdProduct();
				Long quantity = cart.getQuantity();
				Product product = productRepository.getById(idProduct);
				CartResponse cartResponse = new CartResponse(idCart, product, quantity);
				listProducts.add(cartResponse);
			}		
		}
		else {	
			// Nếu đã có token -> get giỏ hàng từ database tương ứng với idUser:
			Long idUser = jwtTokenProvider.getUserIdFromJWT(token);
			User user = userRepository.getById(idUser);
			List<Cart> listCarts = cartRepository.findByIdUser(idUser);
			if( listCarts != null ) {
				for(Cart cart: listCarts) {
					Long idCart = cart.getId();
					Long idProduct = cart.getIdProduct();
					Long quantity = cart.getQuantity();
					Product product = productRepository.getById(idProduct);
					CartResponse cartResponse = new CartResponse(idCart, product, quantity);
					listProducts.add(cartResponse);
				}							
			}
			modelAndView.addObject("userInfo", user);
		}
		
		session.setAttribute("listProducts", listProducts);
		modelAndView.addObject("listProducts", listProducts);
		return modelAndView;
	}
	
	
	
}
