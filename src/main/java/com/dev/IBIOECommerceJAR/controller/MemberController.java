package com.dev.IBIOECommerceJAR.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/member")
public class MemberController {

	@GetMapping({"" , "/index"})
	@ResponseBody
	public String memberIndex() {
		
		return "memberIndex";
	}
	
	@GetMapping("/myInfo")
	public String myInfo() {
		
		return "front/member/myInfo";
	}
	
	@GetMapping("/myTrading")
	public String myTrading() {
		
		return "front/member/myTrading";
	}
	
	@GetMapping("/myPayment")
	public String myPayment() {
		
		return "front/member/myPayment";
	}
	
	@GetMapping("/myDelivery")
	public String myDelivery() {
		
		return "front/member/myDelivery";
	}
	
	@GetMapping("/viewCart")
	public String viewCart() {
		
		return "front/member/viewCart";
	}
	
	@GetMapping("/checkOut")
	public String checkOut() {
		
		return "front/member/checkOut";
	}
	
	@GetMapping("/wishList")
	public String wishList() {
		
		return "front/member/wishList";
	}
	
	@GetMapping("/deliveryDetail")
	public String deliveryDetail() {
		
		return "front/member/detail/deliveryDetail";
	}
	
	@GetMapping("/tradingDetail")
	public String tradingDetail() {
		
		return "front/member/detail/tradingDetail";
	}
	
	@GetMapping("/paymentDetail")
	public String paymentDetail() {
		
		return "front/member/detail/paymentDetail";
	}
	
	@GetMapping("/refundProduct")
	public String refundProduct() {
		
		return "front/member/refundProduct";
	}
	
	@GetMapping("/couponInsert")
	public String couponInsert() {
		
		return "front/member/couponInsert";
	}
	
	@GetMapping("/paymentResult")
	public String paymentResult() {
		
		return "front/member/paymentResult";
	}
}
