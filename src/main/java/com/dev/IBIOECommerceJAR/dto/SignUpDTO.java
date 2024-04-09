package com.dev.IBIOECommerceJAR.dto;

import lombok.Data;

@Data
public class SignUpDTO {

	private String username;
	private String password;
	private String phone;
	private String name;
	private String email;
	private String business;
	private String businessCode;
	private String telephone;
	private String fax;
	private String address;
	private String postal;
	
	private String deliveryAddress;
	private String deliveryPostal;
	
}
