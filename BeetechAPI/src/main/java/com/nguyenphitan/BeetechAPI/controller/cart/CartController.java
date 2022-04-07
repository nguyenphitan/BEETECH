package com.nguyenphitan.BeetechAPI.controller.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenphitan.BeetechAPI.entity.Cart;
import com.nguyenphitan.BeetechAPI.entity.Product;
import com.nguyenphitan.BeetechAPI.jwt.JwtTokenProvider;
import com.nguyenphitan.BeetechAPI.payload.ProductRequest;
import com.nguyenphitan.BeetechAPI.repository.CartRepository;
import com.nguyenphitan.BeetechAPI.repository.ProductRepository;

@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@GetMapping()
	public List<Product> getAll() {
		List<Cart> listCarts = cartRepository.findAll();
		List<Long> productIds = new ArrayList<Long>();
		for(Cart cart : listCarts) {
			productIds.add(cart.getIdProduct());
		}
		return productRepository.findAllById(productIds);
	}
	
	@PostMapping()
	public Cart addToCart(@Valid @RequestBody ProductRequest productRequest, HttpServletRequest request)  {
		// Lấy user id user từ mã token:
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute("token");
		Long idUser = jwtTokenProvider.getUserIdFromJWT(token);
		Long idProduct = productRequest.getIdProduct();
		Long quantitySelected = productRequest.getQuantitySelected();
		
		// Update số lượng của sản phẩm trong bảng sản phẩm:
//		Product product = productRepository.getById(idProduct);
//		Long quantityProduct = product.getQuantity() - quantitySelected;
//		product.setQuantity(quantityProduct);
//		productRepository.save(product);
		
		// Update số lượng sản phẩm trong giỏ hàng:
		// Tìm kiếm sản phẩm có id = id sản phẩm được thêm:
		// Nếu có -> update số lượng sản phẩm trong giỏ hàng (thêm) ứng với user id.
		// Nếu không có -> thêm mới
		List<Cart> listCarts = cartRepository.findByIdProduct(idProduct);
		if( !listCarts.isEmpty() ) {
			for(Cart cart : listCarts) {
				if( cart.getIdUser() == idUser ) {
					Long quantity = cart.getQuantity() + quantitySelected;
					cart.setQuantity(quantity);
					return cartRepository.save(cart);
				}
			}
		}
		Cart cart = new Cart();
		cart.setIdProduct(idProduct);
		cart.setIdUser(idUser);
		cart.setQuantity(quantitySelected);			
		return cartRepository.save(cart);
	}
	
	@PutMapping("/{id}")
	public Cart updateCart(@PathVariable("id") Long id, @Valid @RequestBody Cart cart) {
		return cartRepository.save(cart);
	}

	@DeleteMapping("/{id}")
	public void deleteCart(@PathVariable("id") Long id) {
		cartRepository.deleteById(id);
	}
	
}
