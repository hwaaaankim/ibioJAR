package com.dev.IBIOECommerceJAR.service.authentication;

//public class LogoutService implements LogoutHandler{
//
//	
//	 @Override
//	  public void logout(
//	      HttpServletRequest request,
//	      HttpServletResponse response,
//	      Authentication authentication
//	  ) {
//	    final String authHeader = request.getHeader("Authorization");
//	    final String jwt;
//	    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
//	      return;
//	    }
//	    jwt = authHeader.substring(7);
//	    var storedToken = tokenRepository.findByToken(jwt)
//	        .orElse(null);
//	    if (storedToken != null) {
//	      storedToken.setExpired(true);
//	      storedToken.setRevoked(true);
//	      tokenRepository.save(storedToken);
//	      SecurityContextHolder.clearContext();
//	    }
//	  }
//}
