package com.dev.IBIOECommerceJAR.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dealer")
public class DealerController {

	@GetMapping({"", "/index", "/"})
	public String dealerIndex() {
		
		return "administration/common/index";
	}
	
	@GetMapping("/memberInquiryManager")
	public String memberInquiryManager() {
		
		return "administration/dealer/member/select/memberInquiryManager";
	}
	
	@GetMapping("/refundManager")
	public String refundManager() {
		
		return "administration/dealer/member/select/refundManager";
	}
	
	@GetMapping("/couponManager")
	public String couponManager() {
		
		return "administration/dealer/member/select/couponManager";
	}
	
	@GetMapping("/dealerProductManager")
	public String dealerProductManager() {
		
		return "administration/dealer/product/select/dealerProductManager";
	}
	
	@GetMapping("/dealerProductDetail")
	public String dealerProductDetail() {
		
		return "administration/dealer/product/detail/dealerProductDetail";
	}
	
	@GetMapping("/dealerProductInsert")
	public String dealerProductInsert() {
		
		return "administration/dealer/product/insert/dealerProductInsert";
	}
	
	@PostMapping("/dealerProductUpdate")
	public String dealerProductUpdate() {
		
		return "administration/dealer/product/select/dealerProductManager";
	}
	
	@GetMapping("/dealerProductDelete")
	public String dealerProductDelete() {
		
		return "administration/dealer/product/select/dealerProductManager";
	}
	
	@GetMapping("/dealerProductInfoDumpManager")
	public String dealerProductInfoDumpManager() {
		
		return "administration/dealer/product/select/dealerProductInfoDumpManager";
	}

	@GetMapping("/dealerProductFileDumpManager")
	public String dealerProductFileDumpManager() {
		
		return "administration/dealer/product/select/dealerProductFileDumpManager";
	}
	
	@GetMapping("/dealerPaymentManager")
	public String dealerPaymentManager() {
		
		return "administration/dealer/basic/select/dealerPaymentManager";
	}
	
	@GetMapping("/dealerSellingManager")
	public String dealerSellingManager() {
		
		return "administration/dealer/basic/select/dealerSellingManager";
	}
	
	@GetMapping("/dealerCalculationManager")
	public String dealerCalculationManager() {
		
		return "administration/dealer/basic/select/dealerCalculationManager";
	}
	
	@GetMapping("/dealerBillManager")
	public String dealerBillManager() {
		
		return "administration/dealer/basic/select/dealerBillManager";
	}
}


























