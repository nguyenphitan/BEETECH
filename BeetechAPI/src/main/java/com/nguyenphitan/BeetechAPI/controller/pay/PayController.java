package com.nguyenphitan.BeetechAPI.controller.pay;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/pay")
public class PayController {

	@PostMapping()
	public RedirectView createBill() {
		
		
		return new RedirectView("/bill");
	}
	
}
