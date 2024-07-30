package com.dev.IBIOECommerceJAR.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dev.IBIOECommerceJAR.dto.shopping.OrderViewDTO;
import com.dev.IBIOECommerceJAR.repository.shopping.OrderRepository;
import com.dev.IBIOECommerceJAR.service.shopping.OrderService;

@Controller
@RequestMapping("/admin")
public class AdminOrderController {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderService orderService;
	
	@GetMapping("/orderManager")
	public String orderManager(
			Model model,
			@PageableDefault(size = 10) Pageable pageable
			) {
		Page<OrderViewDTO> orders = orderService.getOrders(pageable);
		
		int startPage = 0;
		int endPage = 0;
		startPage = Math.max(1, orders.getPageable().getPageNumber() - 4);
		endPage = Math.min(orders.getTotalPages(), orders.getPageable().getPageNumber() + 4);
		model.addAttribute("endPage", endPage);
		model.addAttribute("order", orders);
		model.addAttribute("startPage", startPage);
		return "administration/ibio/common/orderManager";
	}
}
