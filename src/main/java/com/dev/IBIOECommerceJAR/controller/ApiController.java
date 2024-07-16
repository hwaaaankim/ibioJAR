package com.dev.IBIOECommerceJAR.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dev.IBIOECommerceJAR.dto.bankda.OrderCheckDTO;
import com.dev.IBIOECommerceJAR.dto.bankda.OrderDTO;
import com.dev.IBIOECommerceJAR.dto.bankda.PaymentCheckDTO;
import com.dev.IBIOECommerceJAR.dto.bankda.ResultDTO;
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
	
	@RequestMapping(value="/unCheckedOrderLists" , method = {RequestMethod.GET, RequestMethod.POST})
	public Map<String, List<OrderDTO>> unCheckedOrderLists(
			){
		
		
		Map<String, List<OrderDTO>> orders = new HashMap<>();
		List<OrderDTO> list = new ArrayList<OrderDTO>();
		OrderDTO one = new OrderDTO();
		one.setOrder_id(1l);
		one.setBuyer_name("test01");
		one.setBilling_name("test01");
		one.setBank_account_no("111-111-111");
		one.setBank_code_name("bank01");
		one.setOrder_date(new Date());
		one.setOrder_price_amount(10000);
		
		OrderDTO two = new OrderDTO();
		two.setOrder_id(2l);
		two.setBuyer_name("test02");
		two.setBilling_name("test02");
		two.setBank_account_no("222-222-222");
		two.setBank_code_name("bank02");
		two.setOrder_date(new Date());
		two.setOrder_price_amount(20000);
		
		OrderDTO three = new OrderDTO();
		three.setOrder_id(3l);
		three.setBuyer_name("test03");
		three.setBilling_name("test03");
		three.setBank_account_no("333-333-333");
		three.setBank_code_name("bank03");
		three.setOrder_date(new Date());
		three.setOrder_price_amount(30000);
		list.add(one);
		list.add(two);
		list.add(three);
		orders.put("orders", list);
		return orders;
	}
	
	@PostMapping("/orderDetail")
	public ResponseEntity<Map<String, Object>> orderDetail(
			@RequestBody OrderCheckDTO dto
			){
		Map<String, Object> response = new HashMap<>();
		OrderDTO one = new OrderDTO();
		one.setOrder_id(1l);
		one.setBuyer_name("test01");
		one.setBilling_name("test01");
		one.setBank_account_no("111-111-111");
		one.setBank_code_name("bank01");
		one.setOrder_date(new Date());
		one.setOrder_price_amount(10000);
		
		OrderDTO two = new OrderDTO();
		two.setOrder_id(2l);
		two.setBuyer_name("test02");
		two.setBilling_name("test02");
		two.setBank_account_no("222-222-222");
		two.setBank_code_name("bank02");
		two.setOrder_date(new Date());
		two.setOrder_price_amount(20000);
		
		OrderDTO three = new OrderDTO();
		three.setOrder_id(3l);
		three.setBuyer_name("test03");
		three.setBilling_name("test03");
		three.setBank_account_no("333-333-333");
		three.setBank_code_name("bank03");
		three.setOrder_date(new Date());
		three.setOrder_price_amount(30000);
		
		if(dto.getOrder_id() == 1l) {
			response.put("order", one);
		}else if(dto.getOrder_id() == 2l){
			response.put("order", two);
		}else if(dto.getOrder_id() == 3l) {
			response.put("order", three);
		}else {
			response.put("return_code", "415");
			response.put("description", "order_id 오류");
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/paymentChecks")
	public ResponseEntity<Map<String, Object>> paymentChecks(
			@RequestBody PaymentCheckDTO requests
			){
		Map<String, Object> response = new HashMap<>();
		List<String> noneId = new ArrayList<String>();
		List<Long> ids = new ArrayList<Long>();
		ids.add(1l);
		ids.add(2l);
		ids.add(3l);

		List<ResultDTO> resultList = new ArrayList<ResultDTO>();
		for(int a=0; a<requests.getRequests().size(); a++) {
			if(!ids.contains(Long.valueOf(String.valueOf(requests.getRequests().get(a).get("order_id"))))) {
				noneId.add((String)requests.getRequests().get(a).get("order_id"));
				ResultDTO dto = new ResultDTO();
				dto.setOrder_id((String)requests.getRequests().get(a).get("order_id"));
				dto.setDescription("order_id 오류");
				resultList.add(dto);
			}
		}
		
		if(noneId.size()>0) {
			response.put("return_code", "415");
			response.put("description", "오류 order_id 체크");
			response.put("orders", resultList);
		}else {
			response.put("return_code", "200");
			response.put("description", "정상");
		}
		
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}

















