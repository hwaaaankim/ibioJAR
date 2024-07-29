package com.dev.IBIOECommerceJAR.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    private static final String VIEW_PATH = "error/";

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.valueOf(status.toString());

            String errorType = (String) request.getAttribute("error_type");
            String description = (String) request.getAttribute("description");
            String details = (String) request.getAttribute("details");

            model.addAttribute("error_type", errorType != null ? errorType : "Unknown");
            model.addAttribute("description", description != null ? description : "No description available.");
            model.addAttribute("details", details != null ? details : "No details available.");

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return VIEW_PATH + "404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return VIEW_PATH + "500";
            } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                return VIEW_PATH + "400";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return VIEW_PATH + "403";
            } else {
                return VIEW_PATH + "error";
            }
        }
        return VIEW_PATH + "error";
    }
}
