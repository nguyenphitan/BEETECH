package com.nguyenphitan.BeetechAPI.controller.cart;

import java.util.ArrayList;
import java.util.Iterator;
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

import com.nguyenphitan.BeetechAPI.model.Cart;

@RestController
@RequestMapping("/clone")
public class CloneCartController {
	@PostMapping("/cart")
	public Cart addProductToCloneCart(@Valid @RequestBody Long id, HttpServletRequest request) {
		Cart cart = new Cart();
		cart.setId_product(id);
		cart.setQuantity(1L);
		
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
			Long proId = cart.getId_product();
			if( productId == proId ) {
				carts.remove(cart);
				break;
			}
		}
	}
	
}
