package com.dev.IBIOECommerceJAR.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.IBIOECommerceJAR.dto.bankda.OrderCheckDTO;
import com.dev.IBIOECommerceJAR.dto.bankda.OrderDTO;
import com.dev.IBIOECommerceJAR.model.authentication.Member;
import com.dev.IBIOECommerceJAR.service.authentication.MemberService;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

	@Autowired
	MemberService memberService;
	
	@PostMapping("/insertMember")
	public String insertMember(
			Member member
			) {
		
		memberService.insertAdmin(member);
		return "success";
	}
	
	@PostMapping("/unCheckedOrderLists")
	public Map<String, List<OrderDTO>> unCheckedOrderLists(
			){
		
		
		Map<String, List<OrderDTO>> orders = new HashMap<>();
		List<OrderDTO> list = new ArrayList<OrderDTO>();
		OrderDTO one = new OrderDTO();
		OrderDTO two = new OrderDTO();
		OrderDTO three = new OrderDTO();
		
		orders.put("orders", list);
		return orders;
	}
	
	@PostMapping("/orderDetail")
	public Map<String, List<OrderDTO>> orderDetail(
			OrderCheckDTO dto
			){
		
		System.out.println(dto.getOrder_id());
		Map<String, List<OrderDTO>> orders = new HashMap<>();
		List<List<OrderDTO>> list = new ArrayList<List<OrderDTO>>();
		OrderDTO one = new OrderDTO();
		OrderDTO two = new OrderDTO();
		OrderDTO three = new OrderDTO();
		
		
		return orders;
	}
	
	@PostMapping("/paymentChecks")
	public Map<String, List<OrderDTO>> paymentChecks(
			List<OrderCheckDTO> requests
			){
		for(OrderCheckDTO dto : requests) {
			System.out.println(dto.getOrder_id());
		}
		
		Map<String, List<OrderDTO>> orders = new HashMap<>();
		List<List<OrderDTO>> list = new ArrayList<List<OrderDTO>>();
		OrderDTO one = new OrderDTO();
		OrderDTO two = new OrderDTO();
		OrderDTO three = new OrderDTO();
		
		
		return orders;
	}
}

















