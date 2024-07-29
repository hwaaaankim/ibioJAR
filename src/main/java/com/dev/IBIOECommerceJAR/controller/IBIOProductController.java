package com.dev.IBIOECommerceJAR.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dev.IBIOECommerceJAR.model.product.Product;
import com.dev.IBIOECommerceJAR.repository.product.ProductRepository;
import com.dev.IBIOECommerceJAR.service.product.ProductService;

@Controller
public class IBIOProductController {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ProductService productService;
	
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
	
	@GetMapping("/productDetailView/{id}")
	public String productDetailView(
			@PathVariable Long id
			) {
		
		return "front/common/product/temp/productDetail" + id;
	}
	
	@GetMapping("/productQuickView/{id}")
	public String productQuickView(
			@PathVariable Long id,
			Model model
			) {
		model.addAttribute("test", "123");
		return "front/common/product/productQuickView";
	}
	
	@RequestMapping(value = "/productCompare",  method = {RequestMethod.POST , RequestMethod.GET})
	public String productCompare(
			@RequestParam Long[] ids
			) {
		
		return "front/common/product/productCompare";
	}
}
