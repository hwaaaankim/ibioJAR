package com.dev.IBIOECommerceJAR.dto;

import lombok.Data;

@Data
public class MemberDTO {

	
	private Long id;
	private String role;
	private int addedDiscount;
	private int commission;
	private int caculate;
}
