package com.dev.IBIOECommerceJAR.service.product;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dev.IBIOECommerceJAR.model.product.Product;

@Service
public class ProductImageService {
	
	public void imageUpload(
			Product product,
			List<MultipartFile> images
			) {
	
		for(MultipartFile f : images) {
			System.out.println(f.getName());
		}
	}

}
