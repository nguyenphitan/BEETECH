package com.nguyenphitan.BeetechAPI.controller.cart;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenphitan.BeetechAPI.entity.Cart;
import com.nguyenphitan.BeetechAPI.payload.ProductRequest;

@RestController
@RequestMapping("/clone")
public class CloneCartController {
	@PostMapping("/cart")
	public Cart addProductToCloneCart(@Valid @RequestBody ProductRequest productRequest, HttpServletRequest request) {
		Cart cart = new Cart();
		cart.setIdProduct(productRequest.getIdProduct());
		cart.setIdUser(-1L);
		cart.setQuantity(productRequest.getQuantitySelected());
		
		HttpSession session = request.getSession();
		List<Cart> cartsSession = (List<Cart>) session.getAttribute("cartsSession");
		if( cartsSession == null ) {
			cartsSession = new ArrayList<Cart>();
		}
		cartsSession.add(cart);
		session.setAttribute("cartsSession", cartsSession);
		return cart;
	}
	
	@DeleteMapping("/cart/{id}")
	public void deleteCartSession(@PathVariable("id") Long productId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<Cart> carts = (List<Cart>) session.getAttribute("cartsSession");
		for(Cart cart : carts) {
			Long proId = cart.getIdProduct();
			if( productId == proId ) {
				carts.remove(cart);
				break;
			}
		}
	}
	
}
