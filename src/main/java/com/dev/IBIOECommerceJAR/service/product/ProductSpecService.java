package com.dev.IBIOECommerceJAR.service.product;

import org.springframework.stereotype.Service;

import com.dev.IBIOECommerceJAR.model.product.Product;

@Service
public class ProductSpecService {

	public void insertSpec(
			Product product,
			String spec
			) {
		System.out.println(spec);
	}
}
