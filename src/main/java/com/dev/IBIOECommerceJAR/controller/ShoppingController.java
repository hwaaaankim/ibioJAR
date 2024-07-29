package com.dev.IBIOECommerceJAR.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.codec.EncoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.IBIOECommerceJAR.dto.CartSummary;
import com.dev.IBIOECommerceJAR.dto.CartUpdateRequest;
import com.dev.IBIOECommerceJAR.dto.ProductCartDTO;
import com.dev.IBIOECommerceJAR.model.authentication.Member;
import com.dev.IBIOECommerceJAR.model.authentication.PrincipalDetails;
import com.dev.IBIOECommerceJAR.model.order.Order;
import com.dev.IBIOECommerceJAR.model.product.Product;
import com.dev.IBIOECommerceJAR.repository.product.ProductRepository;
import com.dev.IBIOECommerceJAR.service.SMSService;
import com.dev.IBIOECommerceJAR.service.product.ProductService;
import com.dev.IBIOECommerceJAR.service.shopping.OrderService;

@Controller
@RequestMapping("/shopping")
public class ShoppingController {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	SMSService smsService;
	
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

    @PostMapping("/viewCart")
    public String viewCart(
    		@RequestParam List<Long> ids, 
    		@RequestParam List<Integer> quantities, 
    		Model model) {
        List<Product> cartProducts = productService.findProductsByIds(ids);
        Map<Long, Integer> quantitiesMap = new HashMap<>();
        int totalPrice = 0;

        for (int i = 0; i < ids.size(); i++) {
            quantitiesMap.put(ids.get(i), quantities.get(i));
            totalPrice += cartProducts.get(i).getProductPrice() * quantities.get(i);
        }

        int taxPrice = (int) (totalPrice * 0.1);
        int finalPrice = totalPrice + taxPrice;

        NumberFormat numberFormat = NumberFormat.getInstance(Locale.KOREA);
        String formattedTotalPrice = numberFormat.format(totalPrice);
        String formattedTaxPrice = numberFormat.format(taxPrice);
        String formattedFinalPrice = numberFormat.format(finalPrice);

        model.addAttribute("products", cartProducts);
        model.addAttribute("quantities", quantitiesMap);
        model.addAttribute("totalPrice", formattedTotalPrice);
        model.addAttribute("taxPrice", formattedTaxPrice);
        model.addAttribute("finalPrice", formattedFinalPrice);
        return "front/member/viewCart";
    }

    @RequestMapping(value = "/checkOut", method = {RequestMethod.POST, RequestMethod.GET})
    public String checkout(
    		@RequestParam List<Long> ids, 
    		@RequestParam List<Integer> quantities, 
    		@AuthenticationPrincipal PrincipalDetails principalDetails,
    		Model model) {
        
    	List<Product> cartProducts = productService.findProductsByIds(ids);
        Map<Long, Integer> quantitiesMap = new HashMap<>();
        int totalPrice = 0;
        for (int i = 0; i < ids.size(); i++) {
            quantitiesMap.put(ids.get(i), quantities.get(i));
            totalPrice += cartProducts.get(i).getProductPrice() * quantities.get(i);
        }
        
        int taxPrice = (int) (totalPrice * 0.1);
        int finalPrice = totalPrice + taxPrice;

        NumberFormat numberFormat = NumberFormat.getInstance(Locale.KOREA);
        String formattedTotalPrice = numberFormat.format(totalPrice);
        String formattedTaxPrice = numberFormat.format(taxPrice);
        String formattedFinalPrice = numberFormat.format(finalPrice);

        model.addAttribute("totalPrice", formattedTotalPrice);
        model.addAttribute("taxPrice", formattedTaxPrice);
        model.addAttribute("finalPrice", formattedFinalPrice);
        
        model.addAttribute("user", principalDetails.getMember());
        model.addAttribute("products", cartProducts);
        model.addAttribute("quantities", quantitiesMap);
        return "front/member/checkOut";
    }
    
    @RequestMapping(
    		value = "/checkoutProcess", 
    		method = {RequestMethod.POST, RequestMethod.GET}
    		)
    @ResponseBody
    public String checkoutProcess(
    		@RequestParam List<Long> ids, 
    		@RequestParam List<Integer> quantities, 
    		@AuthenticationPrincipal PrincipalDetails principalDetails,
    		Model model) throws EncoderException {
    	
    	String buyerMessage = "";
    	Member orderBuyer = principalDetails.getMember();
        Order order = orderService.createOrder(ids, quantities, orderBuyer);
        
        String orderSummary = orderService.generateOrderSummary(order);
        int totalPrice = new BigDecimal(order.getOrderTotalPrice()).intValue();
       
        buyerMessage = orderSummary;
        smsService.sendMessage(orderBuyer.getPhone(), "주문이 완료 되었습니다. 주문번호는 " + order.getOrderId() 
        + "이며, 주문하신 내역은 " + buyerMessage + ", 총 결제 금액은 " + totalPrice + "입니다. 감사합니다.", "L");
        smsService.sendMessage("010-3894-3849", "결제 방식 계좌이체로 주문이 발생하였습니다.", "S");
        String msg = "주문이 완료 되었습니다. 감사합니다.";
		StringBuilder sb = new StringBuilder();
		sb.append("alert('"+msg+"');");
		sb.append("location.href='/index'");
		sb.insert(0, "<script>");
		sb.append("</script>");
		return sb.toString();
    }
    
	@GetMapping("/wishList")
	public String wishList() {
		
		return "front/member/wishList";
	}
}
