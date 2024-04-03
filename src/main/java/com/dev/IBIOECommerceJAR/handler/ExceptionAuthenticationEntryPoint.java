package com.dev.IBIOECommerceJAR.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ExceptionAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
    public void commence(
    		HttpServletRequest request, 
    		HttpServletResponse response, 
    		AuthenticationException authException
    		) throws java.io.IOException {
    	System.out.println("commence");
    	setResponse(response);
    }
    
    private void setResponse(HttpServletResponse response) throws IOException, java.io.IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.sendRedirect("/error/403");
    }
	
}
