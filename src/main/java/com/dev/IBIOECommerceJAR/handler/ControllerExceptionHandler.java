package com.dev.IBIOECommerceJAR.handler;

import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.dev.IBIOECommerceJAR.model.ControllerException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(value= {SQLException.class})
	public ResponseEntity<Object> handleSqlException(SQLException e){
		System.out.println("ControllerExceptionHandler");
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		ControllerException controllerException = new ControllerException(
				"SQL Exception",
				status,
                ZonedDateTime.now(ZoneId.of("Z"))
				);
		
		return new ResponseEntity<>(controllerException, status);
	}
	
	@ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleInvalidFormatException(InvalidFormatException ex, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error/400");
        mav.addObject("error_type", "InvalidFormatException");
        mav.addObject("description", "JSON format 오류!");
        mav.addObject("details", ex.getMessage());
        return mav;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFoundException(NoHandlerFoundException ex, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error/404");
        mav.addObject("error_type", "NoHandlerFoundException");
        mav.addObject("description", "요청한 리소스를 찾을 수 없습니다.");
        mav.addObject("details", ex.getMessage());
        return mav;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error/403");
        mav.addObject("error_type", "AccessDeniedException");
        mav.addObject("description", "접근이 거부되었습니다.");
        mav.addObject("details", ex.getMessage());
        return mav;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleException(Exception ex, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error/500");
        mav.addObject("error_type", ex.getClass().getSimpleName());
        mav.addObject("description", "서버 오류가 발생했습니다.");
        mav.addObject("details", ex.getMessage());
        return mav;
    }

}
