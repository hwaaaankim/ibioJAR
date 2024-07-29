package com.dev.IBIOECommerceJAR.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCartDTO {
	
	private Long id;
	private String name;
	private String image;
	private String price;
	private String code;
	
}
