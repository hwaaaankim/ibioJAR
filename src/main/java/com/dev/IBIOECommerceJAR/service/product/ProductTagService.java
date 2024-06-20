package com.dev.IBIOECommerceJAR.service.product;

import org.springframework.stereotype.Service;

import com.dev.IBIOECommerceJAR.model.product.Product;

@Service
public class ProductTagService {

	public void insertTags(
			Product product,
			String tags
			) {
		System.out.println(tags);
	}
}
