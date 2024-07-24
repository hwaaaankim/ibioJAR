package com.dev.IBIOECommerceJAR.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.IBIOECommerceJAR.dto.ProductDTO;
import com.dev.IBIOECommerceJAR.exception.DeleteViolationException;
import com.dev.IBIOECommerceJAR.model.product.BigSort;
import com.dev.IBIOECommerceJAR.model.product.MiddleSort;
import com.dev.IBIOECommerceJAR.model.product.Product;
import com.dev.IBIOECommerceJAR.model.product.SmallSort;
import com.dev.IBIOECommerceJAR.repository.product.BigSortRepository;
import com.dev.IBIOECommerceJAR.repository.product.MiddleSortRepository;
import com.dev.IBIOECommerceJAR.repository.product.ProductRepository;
import com.dev.IBIOECommerceJAR.repository.product.SmallSortRepository;
import com.dev.IBIOECommerceJAR.service.product.BigSortService;
import com.dev.IBIOECommerceJAR.service.product.MiddleSortService;
import com.dev.IBIOECommerceJAR.service.product.ProductFileService;
import com.dev.IBIOECommerceJAR.service.product.ProductImageService;
import com.dev.IBIOECommerceJAR.service.product.ProductOptionService;
import com.dev.IBIOECommerceJAR.service.product.ProductService;
import com.dev.IBIOECommerceJAR.service.product.ProductSpecService;
import com.dev.IBIOECommerceJAR.service.product.ProductTagService;
import com.dev.IBIOECommerceJAR.service.product.SmallSortService;

@Controller
@RequestMapping("/admin")
public class AdminProductController {

	@Autowired
	BigSortRepository bigSortRepository;
	
	@Autowired
	BigSortService bigSortService;
	
	@Autowired
	MiddleSortRepository middleSortRepository;
	
	@Autowired
	MiddleSortService middleSortService;
	
	@Autowired
	SmallSortRepository smallSortRepository;
	
	@Autowired
	SmallSortService smallSortService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ProductOptionService productOptionService;
	
	@Autowired
	ProductImageService productImageService;
	
	@Autowired
	ProductFileService productFileService;
	
	@Autowired
	ProductTagService productTagService;
	
	@Autowired
	ProductSpecService productSpecService;
	
	@GetMapping("/ibioProductManager")
	public String ibioProductManager(
			Model model,
			@PageableDefault(size=10) Pageable pageable,
			@RequestParam(required = false, defaultValue = "0") Long bigId,
			@RequestParam(required = false, defaultValue = "0") Long middleId,
			@RequestParam(required = false, defaultValue = "0") Long smallId,
			@RequestParam(required = false) Integer minCost,
	        @RequestParam(required = false) Integer maxCost,
	        @RequestParam(required = false) Boolean productSort,
	        @RequestParam(required = false) Boolean productDiscount,
	        @RequestParam(required = false) Boolean sellingResult
			) {
		
		List<BigSort> b = bigSortRepository.findAll();
		if(b.size()<1) {
			BigSort bs = new BigSort();
			bs.setName("분류를 등록 해 주세요");
			bs.setId(0L);
			b.add(bs);
		}
		Page<Product> products = productRepository.findAllByOrderByIdDesc(pageable);
		int startPage = Math.max(1, products.getPageable().getPageNumber() - 4);
		int endPage = Math.min(products.getTotalPages(), products.getPageable().getPageNumber() + 4);
		model.addAttribute("products", products);
		model.addAttribute("bigSorts", b);
		model.addAttribute("bigSortId", bigId);
		model.addAttribute("middleSortId", middleId);
		model.addAttribute("smallSortId", smallId);
		model.addAttribute("maxCost", maxCost);
		model.addAttribute("minCost", minCost);
		model.addAttribute("productSort", productSort);
		model.addAttribute("productDiscount", productDiscount);
		model.addAttribute("sellingResult", sellingResult);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "administration/ibio/product/select/ibioProductManager";
	}
	
	@GetMapping("/ibioProductManagerInsert")
	public String ibioProductManagerInsert(
			Model model
			) {
		
		List<BigSort> b = bigSortRepository.findAll();
		if(b.size()<1) {
			BigSort bs = new BigSort();
			bs.setName("분류를 등록 해 주세요");
			bs.setId(0L);
			b.add(bs);
		}
		
		model.addAttribute("bigsorts", b);
		return "administration/ibio/product/insert/ibioProductManagerInsert";
	}
	
	@PostMapping("/productInsert")
	public String productInsert(
			ProductDTO dto
			) throws IllegalStateException, IOException {

		Product savedProduct = productService.productInsert(dto);
		
		if(dto.getChangeOptionName() != null 
				&& dto.getChangeOptionValues() != null) {
			productOptionService.insertChangeOption(
				savedProduct,
				dto.getChangeOptionName(),
				dto.getChangeOptionValues()
				);
		}
		
		if(dto.getNoneChangeOptionName() != null 
				&& dto.getNoneChangeOptionValues() != null) {
			productOptionService.insertNoneChangeOption(
				savedProduct,
				dto.getNoneChangeOptionName(),
				dto.getNoneChangeOptionValues()
				);
		}
		
		if(dto.getSlideImages() != null 
				&& !dto.getSlideImages().isEmpty() 
				&& !dto.getSlideImages().get(0).isEmpty()) {
			productImageService.imageUpload(savedProduct, dto.getSlideImages());
		}
		
		if(dto.getAddedFiles() != null
				&& !dto.getAddedFiles().isEmpty()
				&& !dto.getAddedFiles().get(0).isEmpty()) {
			productFileService.fileUpload(savedProduct, dto.getAddedFiles());
		}
		
		if(dto.getProductSpecs() != null) {
			productSpecService.insertSpec(savedProduct, dto.getProductSpecs());
		}
		
		if(dto.getProductTags() != null) {
			productTagService.insertTags(savedProduct, dto.getProductTags());
		}
		
		return "redirect:/admin/ibioProductManager";
	}
	
	@GetMapping("/ibioProductManagerDetail")
	public String ibioProductManagerDetail() {
		
		return "administration/ibio/product/detail/ibioProductManagerDetail";
	}
	
	@GetMapping("/ibioProductCategoryManager")
	public String ibioProductCategoryManager(
			Model model
			) {
		
		List<BigSort> b = bigSortRepository.findAll();
		if(b.size()<1) {
			BigSort bs = new BigSort();
			bs.setName("분류를 등록 해 주세요");
			bs.setId(0L);
			b.add(bs);
		}
		
		model.addAttribute("bigsorts", b);
		return "administration/ibio/product/select/ibioProductCategoryManager";
	}
	
	@PostMapping("/bigsortInsert")
	public String bigsortInsert(
			Model model,
			String bigSorts
			) {
		bigSortService.insertBigSort(bigSorts);
		return "redirect:/admin/ibioProductCategoryManager";
	}
	
	@PostMapping("/bigSortDelete")
	public String bigSortDelete(
			@RequestParam(value = "text[]") Long[] text, 
			Model model) {
		
		try {
			for (Long id : text) {
				bigSortRepository.deleteById(id);
			}
		}catch(DeleteViolationException e) {
			throw new DeleteViolationException();
		}	

		return "redirect:/admin/ibioProductCategoryManager";
	}
	
	@PostMapping("/middlesortInsert")
	public String middlesortInsert(
			String middleSorts, 
			Model model, 
			Long bigId
			) {

		middleSortService.insertMiddleSort(middleSorts, bigSortRepository.findById(bigId).get());
		return "redirect:/admin/ibioProductCategoryManager";
	}
	
	@PostMapping("/searchMiddleSort")
	@ResponseBody
	public List<MiddleSort> searchMiddleSort(
			Model model, 
			Long bigId
			) {

		return middleSortRepository.findAllByBigSort(bigSortRepository.findById(bigId).get());
	}
	
	@PostMapping("/middleSortDelete")
	public String middleSortDelete(
			@RequestParam(value = "text[]") Long[] text, 
			Model model, 
			Long bigId) {
		
		try {
			for (Long id : text) {
				middleSortRepository.deleteById(id);
			}
		}catch(DeleteViolationException e) {
			throw new DeleteViolationException();
		}	
		
		return "redirect:/admin/ibioProductCategoryManager";
	}
	
	@PostMapping("/smallsortInsert")
	public String smallsortInsert(
			String smallSorts, 
			Model model, 
			Long middleId
			) {
		
		smallSortService.insertMiddleSort(smallSorts, middleSortRepository.findById(middleId).get());
		return "redirect:/admin/ibioProductCategoryManager";
	}
	
	@PostMapping("/searchSmallSort")
	@ResponseBody
	public List<SmallSort> searchSmallSort(
			Model model, 
			Long middleId
			) {
		return smallSortRepository.findAllByMiddleSort(middleSortRepository.findById(middleId).get());
	}

	@PostMapping("/smallSortDelete")
	public String smallSortDelete(
			@RequestParam(value = "text[]") Long[] text, 
			Model model) {
		
		try {
			for (Long id : text) {
				smallSortRepository.deleteById(id);
			}
		}catch(DeleteViolationException e) {
			throw new DeleteViolationException();
		}	
		
		return "redirect:/admin/ibioProductCategoryManager";
	}
	
	
	@GetMapping("/ibioProductIndexManager")
	public String ibioProductIndexManager() {
		
		return "administration/ibio/product/select/ibioProductIndexManager";
	}
	
	@GetMapping("/dealerProductManager")
	public String dealerProductManager() {
		
		return "administration/ibio/product/select/dealerProductManager";
	}
	
	@GetMapping("/dealerProductManagerDetail")
	public String dealerProductManagerDetail() {
		
		return "administration/ibio/product/detail/dealerProductManagerDetail";
	}
	
	@GetMapping("/ibioProductInfoDumpManager")
	public String ibioProductInfoDumpManager() {
		
		return "administration/ibio/product/select/ibioProductInfoDumpManager";
	}
	
	@GetMapping("/ibioProductFileDumpManager")
	public String ibioProductFileDumpManager() {
		
		return "administration/ibio/product/select/ibioProductFileDumpManager";
	}
}
