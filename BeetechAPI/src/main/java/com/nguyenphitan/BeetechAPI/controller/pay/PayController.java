package com.nguyenphitan.BeetechAPI.controller.pay;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/pay")
public class PayController {

	@GetMapping("/bill")
	public ModelAndView createBill() {
		ModelAndView modelAndView = new ModelAndView("bill");
		
		return modelAndView;
	}
	
}
