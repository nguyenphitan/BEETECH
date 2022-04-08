package com.nguyenphitan.BeetechAPI.controller.pay;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenphitan.BeetechAPI.entity.Product;
import com.nguyenphitan.BeetechAPI.jwt.JwtTokenProvider;
import com.nguyenphitan.BeetechAPI.payload.CartResponse;
import com.nguyenphitan.BeetechAPI.repository.CartRepository;
import com.nguyenphitan.BeetechAPI.repository.ProductRepository;

@RestController
@RequestMapping("/pay")
public class PayController {

	@Autowired
	CartRepository cartRepository;
	
	@Autowired 
	ProductRepository productRepository;
	
	@Autowired 
	JwtTokenProvider jwtTokenProvider;
	
	@DeleteMapping()
	public void createBill(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute("token");
		
		// Xóa sản phẩm trong giỏ hàng
		// Update số lượng còn lại của mỗi sản phẩm
		List<CartResponse> listProducts = (List<CartResponse>) session.getAttribute("listProducts");
		
		for ( CartResponse cart : listProducts ) {
			Long idCard = cart.getId();
			Long idProduct = cart.getProduct().getId();
			cartRepository.deleteById(idCard);
			Product product = productRepository.getById(idProduct);
			Long quantity = product.getQuantity() - cart.getQuantity();
			product.setQuantity(quantity);
			productRepository.save(product);
		}
		
		// Xóa list sản phẩm khỏi session:
		session.removeAttribute("listProducts");
		
	}
	
}
