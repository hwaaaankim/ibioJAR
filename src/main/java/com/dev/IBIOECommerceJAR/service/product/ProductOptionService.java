package com.dev.IBIOECommerceJAR.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.IBIOECommerceJAR.model.product.Product;
import com.dev.IBIOECommerceJAR.repository.product.ProductOptionRepository;

@Service
public class ProductOptionService {

	@Autowired
	ProductOptionRepository productOptionRepository;
	
	public void insertChangeOption(
			Product product,
			String[] optionName,
			String[] optionValue
			) {
		for(String a : optionName) {
			System.out.println(a);
		}
		
		for(String b : optionValue) {
			System.out.println(b);
		}
	}
	
	public void insertNoneChangeOption(
			Product product,
			String[] optionName,
			String[] optionValue
			) {
		for(String a : optionName) {
			System.out.println(a);
		}
		
		for(String b : optionValue) {
			System.out.println(b);
		}
	}
}

