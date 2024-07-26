package com.dev.IBIOECommerceJAR.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.IBIOECommerceJAR.dto.CartSummary;
import com.dev.IBIOECommerceJAR.dto.CartUpdateRequest;
import com.dev.IBIOECommerceJAR.dto.ProductCartDTO;
import com.dev.IBIOECommerceJAR.model.product.Product;
import com.dev.IBIOECommerceJAR.repository.product.ProductRepository;
import com.dev.IBIOECommerceJAR.service.HtmlRenderer;
import com.dev.IBIOECommerceJAR.service.product.ProductService;

@Controller
public class IBIOProductController {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	HtmlRenderer htmlRenderer;
	
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
	
	@GetMapping("/wishList")
	public String wishList() {
		
		return "front/member/wishList";
	}
	
	@PostMapping("/findProduct")
	@ResponseBody
    public ProductCartDTO findProduct(@RequestParam("id") Long id) {
        Product product = productService.findProductById(id);
        return convertToProductDTO(product);
    }

    @PostMapping("/getCartDetails")
    public List<ProductCartDTO> getCartDetails(@RequestParam List<Long> ids) {
        List<Product> products = productService.findProductsByIds(ids);
        return products.stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());
    }

    private ProductCartDTO convertToProductDTO(Product product) {
        ProductCartDTO productDTO = new ProductCartDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getProductTitle());
        productDTO.setImage(product.getProductImageRoad());
        productDTO.setCode(product.getProductCode());
        productDTO.setPrice(productService.getProductPrice(product)); // Assuming this method returns the price as a String
        return productDTO;
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
	
	@PostMapping("/updateQuantity")
    public ResponseEntity<?> updateQuantity(@RequestBody CartUpdateRequest request) {
        productService.updateProductQuantity(request.getId(), request.getQuantity());
        CartSummary cartSummary = productService.calculateCartSummary();
        Map<String, Object> response = new HashMap<>();
        response.put("totalPrice", productService.getTotalPrice(request.getId()));
        response.put("cartSummary", cartSummary);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/removeProduct")
    public ResponseEntity<?> removeProduct(@RequestBody CartUpdateRequest request) {
        productService.removeProductFromCart(request.getId());
        CartSummary cartSummary = productService.calculateCartSummary();
        Map<String, Object> response = new HashMap<>();
        response.put("cartSummary", cartSummary);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/viewCart")
    public String viewCart(@RequestParam List<Long> ids, @RequestParam List<Integer> quantities, Model model) {
        List<Product> cartProducts = productService.findProductsByIds(ids);
        Map<Long, Integer> quantitiesMap = new HashMap<>();
        for (int i = 0; i < ids.size(); i++) {
            quantitiesMap.put(ids.get(i), quantities.get(i));
        }

        model.addAttribute("products", cartProducts);
        model.addAttribute("quantities", quantitiesMap);
        return "front/member/viewCart";
    }

    @GetMapping("/checkout")
    public String checkout(@RequestParam Long[] ids, @RequestParam Integer[] quantities, Model model) {
        List<Product> cartProducts = productService.findProductsByIds(Arrays.asList(ids));
        Map<Long, Integer> quantitiesMap = new HashMap<>();
        for (int i = 0; i < ids.length; i++) {
            quantitiesMap.put(ids[i], quantities[i]);
        }

        model.addAttribute("products", cartProducts);
        model.addAttribute("quantities", quantitiesMap);
        return "front/member/checkout";
    }
	
	@RequestMapping(value = "/productCompare",  method = {RequestMethod.POST , RequestMethod.GET})
	public String productCompare(
			@RequestParam Long[] ids
			) {
		
		return "front/common/product/productCompare";
	}
	
}
