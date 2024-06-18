package com.dev.IBIOECommerceJAR.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.IBIOECommerceJAR.exception.DeleteViolationException;
import com.dev.IBIOECommerceJAR.model.BigSort;
import com.dev.IBIOECommerceJAR.model.MiddleSort;
import com.dev.IBIOECommerceJAR.model.SmallSort;
import com.dev.IBIOECommerceJAR.repository.product.BigSortRepository;
import com.dev.IBIOECommerceJAR.repository.product.MiddleSortRepository;
import com.dev.IBIOECommerceJAR.repository.product.SmallSortRepository;
import com.dev.IBIOECommerceJAR.service.product.BigSortService;
import com.dev.IBIOECommerceJAR.service.product.MiddleSortService;
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
	
	
	@GetMapping("/ibioProductManager")
	public String ibioProductManager() {
		
		return "administration/ibio/product/select/ibioProductManager";
	}
	
	@GetMapping("/ibioProductManagerInsert")
	public String ibioProductManagerInsert() {
		
		return "administration/ibio/product/insert/ibioProductManagerInsert";
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
