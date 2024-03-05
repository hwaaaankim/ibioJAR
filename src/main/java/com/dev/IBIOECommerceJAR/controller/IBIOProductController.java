package com.dev.IBIOECommerceJAR.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IBIOProductController {

	@GetMapping("/productList")
	public String productList() {
		
		return "front/common/product/productList";
	}
	
	@GetMapping("/productDetail")
	public String productDetail() {
		
		return "front/common/product/productDetail";
	}
	
	@GetMapping("/productQuickView")
	public String productQuickView() {
		
		return "front/common/product/productQuickView";
	}
}
