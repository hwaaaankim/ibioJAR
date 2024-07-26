package com.dev.IBIOECommerceJAR.dto;

import lombok.Data;

@Data
public class CartUpdateRequest {
	private Long id;
    private int quantity;
}
