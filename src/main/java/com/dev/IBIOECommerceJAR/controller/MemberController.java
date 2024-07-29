package com.dev.IBIOECommerceJAR.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.IBIOECommerceJAR.repository.product.ProductRepository;
import com.dev.IBIOECommerceJAR.service.HtmlRenderer;
import com.dev.IBIOECommerceJAR.service.product.ProductService;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	HtmlRenderer htmlRenderer;
	
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
