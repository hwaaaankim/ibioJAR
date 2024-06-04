package com.dev.IBIOECommerceJAR.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/test")
@RestController
public class TestController {
	
	private final String IS_MOBILE = "MOBILE";
	private final String IS_PHONE = "PHONE";
	private final String IS_TABLET = "TABLET";
	private final String IS_PC = "PC";

	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}

	public String isDevice(HttpServletRequest req) {
		String userAgent = req.getHeader("User-Agent").toUpperCase();

		if (userAgent.indexOf(IS_MOBILE) > -1) {
			if (userAgent.indexOf(IS_PHONE) == -1)
				return IS_MOBILE;
			else
				return IS_TABLET;
		} else
			return IS_PC;
	}
	
	
	@GetMapping("/ip")
	public String ipTest(HttpServletRequest request) {
		
		return "id : " + getClientIP(request) + ", Device : " + isDevice(request);
				
	}
}
