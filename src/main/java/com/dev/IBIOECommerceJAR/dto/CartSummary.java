package com.dev.IBIOECommerceJAR.dto;

import lombok.Data;

@Data
public class CartSummary {

	private int totalPrice;
    private int shippingCost;
    private int taxPrice;
    private int finalPrice;

    public CartSummary(int totalPrice, int shippingCost, int taxPrice, int finalPrice) {
        this.totalPrice = totalPrice;
        this.shippingCost = shippingCost;
        this.taxPrice = taxPrice;
        this.finalPrice = finalPrice;
    }
}
