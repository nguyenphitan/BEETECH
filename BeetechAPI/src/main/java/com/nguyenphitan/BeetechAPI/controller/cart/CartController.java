package com.nguyenphitan.BeetechAPI.controller.cart;

import java.util.ArrayList;
import java.util.List;

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

import com.nguyenphitan.BeetechAPI.model.Cart;
import com.nguyenphitan.BeetechAPI.model.Product;
import com.nguyenphitan.BeetechAPI.repository.CartRepository;
import com.nguyenphitan.BeetechAPI.repository.ProductRepository;

@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping()
	public List<Product> getAll() {
		List<Cart> listCarts = cartRepository.findAll();
		List<Long> productIds = new ArrayList<Long>();
		for(Cart cart : listCarts) {
			productIds.add(cart.getId_product());
		}
		return productRepository.findAllById(productIds);
	}
	
//	@PostMapping()
//	public RedirectView addToCart(@RequestParam("id") Long id) throws IOException  {
//		Cart cart = new Cart();
//		cart.setId_product(id);
//		cart.setQuantity(1L);
//		cartRepository.save(cart);
//		return new RedirectView("/public/list-products");
//	}
	
	@PostMapping()
	public Cart addToCart(@Valid @RequestBody Long id)  {
		Cart cart = new Cart();
		cart.setId_product(id);
		cart.setQuantity(1L);
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
