package com.dev.IBIOECommerceJAR.dto.bankda;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class OrderDTO {

	private Long order_id;
	private String buyer_name;
	private String billing_name;
	private String bank_account_no = "630010395746";
	private String bank_code_name = "하나은행";
	private Date order_date;
	private Integer order_price_amount; 
	
	
	private String buyer_email;
	private String buyer_cellphone;
	private List<Map<String, String>> items;
}
