package com.nguyenphitan.BeetechAPI.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.entity.Cart;
import com.nguyenphitan.BeetechAPI.jwt.JwtTokenProvider;
import com.nguyenphitan.BeetechAPI.repository.CartRepository;
import com.nguyenphitan.BeetechAPI.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	public ModelAndView getAllProduct(String page, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView(page);
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
	
}
