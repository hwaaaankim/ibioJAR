package com.dev.IBIOECommerceJAR.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.codec.EncoderException;
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
import com.dev.IBIOECommerceJAR.model.authentication.Member;
import com.dev.IBIOECommerceJAR.service.authentication.MemberService;
import com.dev.IBIOECommerceJAR.service.shopping.OrderService;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

	@Autowired
	MemberService memberService;
	
	@Autowired
    private OrderService orderService;
	
	@PostMapping("/insertMember")
	public String insertMember(
			Member member
			) {
		memberService.insertAdmin(member);
		return "success";
	}
	
	@RequestMapping(
			value="/unCheckedOrderLists" , 
			method = {RequestMethod.GET, RequestMethod.POST}
			)
	public Map<String, List<OrderDTO>> unCheckedOrderLists(
			){
		
		
		List<OrderDTO> orders = orderService.getUncheckedOrders();
        Map<String, List<OrderDTO>> response = new HashMap<>();
        response.put("orders", orders);
        return response;
	}
	
	@PostMapping("/orderDetail")
    public ResponseEntity<Map<String, Object>> orderDetail(@RequestBody OrderCheckDTO dto) {
        Map<String, Object> response = new HashMap<>();

        if (dto.getOrder_id() == null) {
            response.put("return_code", "400");
            response.put("description", "JSON format 오류");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<OrderDTO> orderOptional = orderService.getOrderById(dto.getOrder_id());

        if (orderOptional.isPresent()) {
            response.put("order", orderOptional.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("return_code", "415");
            response.put("description", "order_id 오류");
            return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
	
	@PostMapping("/paymentChecks")
    public ResponseEntity<Map<String, Object>> paymentChecks(@RequestBody PaymentCheckDTO requests) throws EncoderException {
        Map<String, Object> response = new HashMap<>();
        
        if (requests.getRequests() == null) {
            response.put("return_code", "400");
            response.put("description", "JSON format 오류");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        List<Map<String, String>> errors = orderService.checkAndUpdateOrders(requests.getRequests());

        if (errors.isEmpty()) {
            response.put("return_code", "200");
            response.put("description", "정상");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("return_code", "415");
            response.put("description", "오류 order_id 체크");
            response.put("orders", errors);
            return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}

















