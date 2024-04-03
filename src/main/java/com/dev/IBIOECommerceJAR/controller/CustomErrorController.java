package com.dev.IBIOECommerceJAR.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CustomErrorController implements ErrorController{

	private String VIEW_PATH = "/error/";

	@GetMapping("/error")
	public String handleError(
			HttpServletRequest request,
			Model model) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		log.info(status.toString());
		if(status != null) {
			int statusCode = Integer.valueOf(status.toString());
			
			if(statusCode == HttpStatus.NOT_FOUND.value()) {
				return VIEW_PATH+"404";
			}else if(statusCode == 500){
				return VIEW_PATH+"500";
			}else {
				return VIEW_PATH+"403";
			}
		}
		return "error";
	}
	
}
