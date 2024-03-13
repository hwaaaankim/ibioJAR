package com.dev.IBIOECommerceJAR.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CommonController {

	
	@GetMapping({"/", "/index"})
	public String index() {
		log.info("index 접속");
		
		return "front/common/index";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		
		return "front/common/login";
	}
	
	@PostMapping("/signinProcess")
	public String loginProcess() {
		
		return "front/common/index";
	}
	
	@GetMapping("/signupForm")
	public String signupForm() {
		
		return "front/common/signupForm";
	}
	
	@PostMapping("/signupProcess")
	public String signupProcess() {
		
		return "front/common/index";
	}
	
	@GetMapping("/dealerSignupForm")
	public String dealerSignupForm() {
		
		return "front/common/dealer/signupForm";
	}
	
	@GetMapping("/notice")
	public String notice() {
		
		return "front/common/notice/notice";
	}
	
	@GetMapping("/faq")
	public String faq() {
		
		return "front/common/faq";
	}
	
	@GetMapping("/contact")
	public String contact() {
		
		return "front/common/contact";
	}
	
	@GetMapping("/privacy")
	public String privacy() {
		
		return "front/common/privacy";
	}
	
	@GetMapping("/policy")
	public String policy() {
		
		return "front/common/policy";
	}
	
	@GetMapping("/sitemap")
	public String sitemap() {
		
		return "front/common/sitemap";
	}
	
	@GetMapping("/about")
	public String about() {
		
		return "front/common/about";
	}
	
	@GetMapping("/event")
	public String event() {
		
		return "front/common/event/event";
	}
	
	@GetMapping("/findPassword")
	public String findPassword() {
		
		return "front/common/info/findPassword";
	}
	
	@GetMapping("/findUsername")
	public String findUsername() {
		
		return "front/common/info/findUsername";
	}
	
	@GetMapping("/dealerShop")
	public String dealerShop() {
		
		return "front/common/dealer/index";
	}
	
	@GetMapping("/noticeDetail")
	public String noticeDetail() {
		
		return "front/common/notice/noticeDetail";
	}
	
	@GetMapping("/eventDetail")
	public String eventDetail() {
		
		return "front/common/event/eventDetail";
	}
	
	@GetMapping("/productCompare")
	public String productCompare() {
		
		return "front/common/product/productCompare";
	}
	
	
	
	
	
	
	
	
}
