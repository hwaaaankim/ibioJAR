package com.dev.IBIOECommerceJAR.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.IBIOECommerceJAR.service.authentication.MemberService;

@RequestMapping("/test")
@RestController
public class TestController {

	@Autowired
	MemberService memberService;
	
	@GetMapping("/password")
	public String passwordCheck() {
		
		
		return memberService.passwordCheck();
		
	}
}
