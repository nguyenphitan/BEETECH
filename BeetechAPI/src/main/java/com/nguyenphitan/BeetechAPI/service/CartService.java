package com.nguyenphitan.BeetechAPI.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.entity.Cart;
import com.nguyenphitan.BeetechAPI.entity.Product;
import com.nguyenphitan.BeetechAPI.entity.User;
import com.nguyenphitan.BeetechAPI.entity.discount.Discount;
import com.nguyenphitan.BeetechAPI.entity.wallet.UserAccount;
import com.nguyenphitan.BeetechAPI.jwt.JwtTokenProvider;
import com.nguyenphitan.BeetechAPI.payload.CartResponse;
import com.nguyenphitan.BeetechAPI.repository.CartRepository;
import com.nguyenphitan.BeetechAPI.repository.ProductRepository;
import com.nguyenphitan.BeetechAPI.repository.UserRepository;
import com.nguyenphitan.BeetechAPI.repository.discount.DiscountRepository;
import com.nguyenphitan.BeetechAPI.repository.wallet.UserAccountRepository;

@Service
public class CartService {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired 
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	CartRepository cartRepository;
	
	@Autowired 
	DiscountRepository discountRepository;
	
	@Autowired
	UserAccountRepository userAccountRepository;
	
	
	/*
	 * Lấy ra danh sách tất cả các sản phẩm trong giỏ hàng
	 * Created by: NPTAN (18/04/2022)
	 */
	public ModelAndView getAllCart(String page, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView(page);
		
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

		
			// Kiểm tra xem đã liên kết ví điện tử hay chưa?
			UserAccount userAccount = userAccountRepository.findByUserId(idUser);
			session.setAttribute("userAccount", userAccount);
		
		}
		
		session.setAttribute("listProducts", listProducts);
		modelAndView.addObject("listProducts", listProducts);
		handlePayment(modelAndView, listProducts);
		
		return modelAndView;
	}
	
	
	/*
	 * Xử lý nghiệp vụ liên quan đến số tiền phải thanh toán
	 * Created by: NPTAN (18/04/2022)
	 */
	public void handlePayment(ModelAndView modelAndView, List<CartResponse> listProducts) {
		// Tính tổng tiền trong giỏ hàng -> gợi ý discount (DiscountService)
		Double totalCart = 0D;
		for(CartResponse cart : listProducts) {
			Double productPrice = cart.getProduct().getPrice();
			Long quantity = cart.getQuantity();
			Double totalPrice = quantity * productPrice;
			totalCart += totalPrice;
		}
		
		// Gợi ý discount:
		// 1. Lấy ra discount hiện tại: (nếu có)
		Double currentDiscount = 0D;
		Double nextDiscount = 0D;
		Double nextValue = 0D;
		List<Discount> discounts = discountRepository.findAll();
		discounts.sort(Comparator.comparing(Discount::getValue));
		
		if( totalCart < discounts.get(0).getValue() ) {
			nextDiscount = discounts.get(0).getDiscount();
			nextValue = discounts.get(0).getValue();
		} else {
			for ( int i = 1 ; i < discounts.size() ; i++ ) {
				if( totalCart >= discounts.get(i-1).getValue() && totalCart < discounts.get(i).getValue() ) {
					currentDiscount = discounts.get(i-1).getDiscount();
					nextDiscount = discounts.get(i).getDiscount();
					nextValue = discounts.get(i).getValue();
					break;
				}
			}
		}
		
		// 2. Tính toán lại tổng tiền sau khi trừ discount:
		Double discountValue = (totalCart * currentDiscount)/100;
		Double realCart = totalCart - discountValue; 
		
		// 3. Gợi ý mua thêm xx tiền để đạt discount tiếp theo:
		Double valueToNextDiscount = nextValue - totalCart; 
		
		modelAndView.addObject("currentDiscount", currentDiscount);
		modelAndView.addObject("nextDiscount", nextDiscount);
		modelAndView.addObject("discountValue", discountValue);
		modelAndView.addObject("valueToNextDiscount", valueToNextDiscount);
		modelAndView.addObject("totalCart", totalCart);
		modelAndView.addObject("realCart", realCart);
		
	}
	
	
}
