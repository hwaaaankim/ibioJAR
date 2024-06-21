package com.dev.IBIOECommerceJAR.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dev.IBIOECommerceJAR.model.product.Product;
import com.dev.IBIOECommerceJAR.repository.product.ProductRepository;

@Controller
public class IBIOProductController {

	@Autowired
	ProductRepository productRepository;
	
	
	@GetMapping("/productList")
	public String productList(
			Model model,
			@PageableDefault(size = 15) Pageable pageable
			) {
		Page<Product> products = productRepository.findAllByOrderByProductClicks(pageable);
		int startPage = Math.max(1, products.getPageable().getPageNumber() - 4);
		int endPage = Math.min(products.getTotalPages(), products.getPageable().getPageNumber() + 4);
		
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("products", products);
		
		return "front/common/product/productList";
	}
	
	@GetMapping("/productDetail/{id}")
	public String productDetail(
			@PathVariable Long id,
			Model model
			) {
		
		model.addAttribute("product", productRepository.findById(id).get());
		return "front/common/product/productDetail";
	}
	
	@GetMapping("/productQuickView")
	public String productQuickView() {
		
		return "front/common/product/productQuickView";
	}
	
	@GetMapping("/productDetailView/{id}")
	public String productDetailView(
			@PathVariable Long id
			) {
		
		return "front/common/product/temp/productDetail" + id;
	}
	
}
