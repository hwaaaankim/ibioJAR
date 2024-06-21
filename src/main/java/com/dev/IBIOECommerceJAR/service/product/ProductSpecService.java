package com.dev.IBIOECommerceJAR.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.IBIOECommerceJAR.model.product.Product;
import com.dev.IBIOECommerceJAR.model.product.ProductSpec;
import com.dev.IBIOECommerceJAR.repository.product.ProductSpecRepository;

@Service
public class ProductSpecService {

	@Autowired
	ProductSpecRepository productRepository;
	
	public void insertSpec(
			Product product,
			String spec
			) {
		
		String specs[] = spec.split(",");
		for(String s : specs) {
			ProductSpec productSpec = new ProductSpec();
			productSpec.setProductId(product.getId());
			productSpec.setProductSpecSubject(s);
			productRepository.save(productSpec);
		}
		
	}
}
