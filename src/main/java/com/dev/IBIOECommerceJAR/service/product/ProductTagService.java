package com.dev.IBIOECommerceJAR.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.IBIOECommerceJAR.model.product.Product;
import com.dev.IBIOECommerceJAR.model.product.ProductTag;
import com.dev.IBIOECommerceJAR.repository.product.ProductTagRepository;

@Service
public class ProductTagService {

	@Autowired
	ProductTagRepository productTagRepository;
	
	public void insertTags(
			Product product,
			String tag
			) {
		String tags[] = tag.split(",");
		for(String t : tags) {
			ProductTag productTag = new ProductTag();
			productTag.setProductId(product.getId());
			productTag.setProductTagText(t);
			productTagRepository.save(productTag);
		}
	}
}
