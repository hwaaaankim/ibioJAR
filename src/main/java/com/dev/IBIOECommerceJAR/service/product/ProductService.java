package com.dev.IBIOECommerceJAR.service.product;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.dev.IBIOECommerceJAR.dto.CartSummary;
import com.dev.IBIOECommerceJAR.dto.ProductDTO;
import com.dev.IBIOECommerceJAR.model.product.Product;
import com.dev.IBIOECommerceJAR.repository.product.BigSortRepository;
import com.dev.IBIOECommerceJAR.repository.product.MiddleSortRepository;
import com.dev.IBIOECommerceJAR.repository.product.ProductFileRepository;
import com.dev.IBIOECommerceJAR.repository.product.ProductImageRepository;
import com.dev.IBIOECommerceJAR.repository.product.ProductRepository;
import com.dev.IBIOECommerceJAR.repository.product.SmallSortRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	SmallSortRepository smallSortRepository;
	
	@Autowired
	MiddleSortRepository middleSortRepository;
	
	@Autowired
	BigSortRepository bigSortRepository;
	
	@Autowired
	ProductFileRepository productFileRepository;

	@Autowired
	ProductFileService productFileService;
	
	@Autowired
	ProductImageRepository productImageRepository;
	
	@Autowired
	ProductImageService productImageService;
	
	@Autowired
	ProductOptionService productOptionService;
	
	@Value("${spring.upload.env}")
	private String env;

	@Value("${spring.upload.path}")
	private String commonPath;

	private Map<Long, Integer> cart = new HashMap<>();
	
	public Product productInsert(
			ProductDTO dto
			) throws IllegalStateException, IOException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String current_date = simpleDateFormat.format(new Date());

		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();
		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		String productCode = generatedString + "_" + current_date;
		// 실제 파일 저장 위치
		String path = commonPath + "product/ibio/" + current_date + "/";
		// 파일 resource 로드 url
		String road = "/upload/product/ibio/" + current_date + "/";
		
		Product product = new Product();
		product.setProductCode(productCode);
		product.setBigSort(bigSortRepository.findById(dto.getBigId()).get());
		product.setMiddleSort(middleSortRepository.findById(dto.getMiddleId()).get());
		product.setSmallSort(smallSortRepository.findById(dto.getSmallId()).get());
		
		// 제품 브랜드 이미지
		if(dto.getBrand().equals("") || dto.getBrand() == null) {
			product.setProductBrand("-");
			product.setProductBrandImageName("-");
			product.setProductBrandImagePath("-");
			product.setProductBrandImageRoad("-");
		}else {
			product.setProductBrand(dto.getBrand());
			if(dto.getBrandImage() != null && !dto.getBrandImage().isEmpty()) {
				String brandImageContentType = dto.getBrandImage().getContentType();
				String brandImageFileExtension = "";
				if (ObjectUtils.isEmpty(brandImageContentType)) {
					return null;
				} else {
					if (brandImageContentType.contains("image/jpeg")) {
						brandImageFileExtension = ".jpg";
					} else if (brandImageContentType.contains("image/png")) {
						brandImageFileExtension = ".png";
					} 
				}
				String brandImageName = generatedString + brandImageFileExtension;
				String brandImagePath = path + dto.getTitle() + "/brand/" + brandImageName;
				String brandImageRoad = road + dto.getTitle() + "/brand/" + brandImageName;
				
				String brandImageSavePath = brandImagePath;
				File brandImageSaveFile = new File(brandImageSavePath);	
				if (!brandImageSaveFile.exists()) {
					brandImageSaveFile.mkdirs();
				}
				dto.getBrandImage().transferTo(brandImageSaveFile);
				
				product.setProductBrandImageName(brandImageName);
				product.setProductBrandImagePath(brandImagePath);
				product.setProductBrandImageRoad(brandImageRoad);
				
			}else {
				product.setProductBrandImageName("-");
				product.setProductBrandImagePath("-");
				product.setProductBrandImageRoad("-");
			}
		}
		
		if(dto.getDescription() == null || dto.getDescription().equals("")) {
			product.setProductDescription("-");
		}else {
			product.setProductDescription(dto.getDescription());
		}
		
		product.setProductTitle(dto.getTitle());
		product.setProductPrice(dto.getPrice());
		product.setProductPriceTarget(dto.getPriceTarget());
		
		if(dto.getNoneDiscount()!=null 
				&& dto.getMemberDiscount()!=null 
				&& dto.getDealerDiscount()!=null) {
			product.setProductDiscountSign(true);
		}else {
			product.setProductDiscountSign(false);
		}
		
		if(dto.getNoneDiscount()!=null) {
			product.setProductNoneDiscount(dto.getNoneDiscount());
		}else {
			product.setProductNoneDiscount(0);
		}
		
		if(dto.getMemberDiscount()!=null) {
			product.setProductMemberDiscount(dto.getMemberDiscount());
		}else {
			product.setProductMemberDiscount(0);
		}
		
		if(dto.getDealerDiscount()!=null) {
			product.setProductDealerDiscount(dto.getDealerDiscount());
		}else {
			product.setProductDealerDiscount(0);
		}
		
		// product 대표이미지
		String productImageContentType = dto.getProductImage().getContentType();
		String productImageFileExtension = "";
		if (ObjectUtils.isEmpty(productImageContentType)) {
			return null;
		} else {
			if (productImageContentType.contains("image/jpeg")) {
				productImageFileExtension = ".jpg";
			} else if (productImageContentType.contains("image/png")) {
				productImageFileExtension = ".png";
			} 
		}
		String productImageName = generatedString + productImageFileExtension;
		String productImagePath = path + productCode + "/image/" + productImageName;
		String productImageRoad = road + productCode + "/image/" + productImageName;
		
		String productImageSavePath = productImagePath;
		File productImageSaveFile = new File(productImageSavePath);	
		if (!productImageSaveFile.exists()) {
			productImageSaveFile.mkdirs();
		}
		dto.getProductImage().transferTo(productImageSaveFile);
		
		product.setProductImageName(productImageName);
		product.setProductImagePath(productImagePath);
		product.setProductImageRoad(productImageRoad);
	
		// spec 이미지
		if(dto.getSpecImage() != null && !dto.getSpecImage().isEmpty()) {
			String specImageContentType = dto.getSpecImage().getContentType();
			String specImageFileExtension = "";
			if (ObjectUtils.isEmpty(specImageContentType)) {
				return null;
			} else {
				if (specImageContentType.contains("image/jpeg")) {
					specImageFileExtension = ".jpg";
				} else if (specImageContentType.contains("image/png")) {
					specImageFileExtension = ".png";
				} 
			}
			String specImageName = generatedString + specImageFileExtension;
			String specImagePath = path + productCode + "/spec/" + specImageName;
			String specImageRoad = road + productCode + "/spec/" + specImageName;
			
			String specImageSavePath = specImagePath;
			File specImageSaveFile = new File(specImageSavePath);	
			if (!specImageSaveFile.exists()) {
				specImageSaveFile.mkdirs();
			}
			dto.getSpecImage().transferTo(specImageSaveFile);
			
			product.setProductSpecImageName(specImageName);
			product.setProductSpecImagePath(specImagePath);
			product.setProductSpecImageRoad(specImageRoad);
			
		}else {
			product.setProductSpecImageName("-");
			product.setProductSpecImagePath("-");
			product.setProductSpecImageRoad("-");
		}
		if(dto.getEventN().equals("none")) {
			product.setProductEventSign(false);
			product.setProductEventName(dto.getEventN());
			product.setProductEventNoneDiscount(0);
			product.setProductEventMemberDiscount(0);
			product.setProductEventDealerDiscount(0);
		}else {
			product.setProductEventSign(true);
			product.setProductEventName(dto.getEventN());
			product.setProductEventNoneDiscount(dto.getEventNoneD());
			product.setProductEventMemberDiscount(dto.getEventMemberD());
			product.setProductEventDealerDiscount(dto.getEventDealerD());
		}

		Product savedProduct = productRepository.save(product);
		
		return savedProduct;
	}
	
	public Page<Product> findProducts(
			Long bigId, 
			Long middleId, 
			Long smallId, 
			Integer minCost, 
			Integer maxCost, 
			String productSort, 
			String productDiscount, 
			String sellingResult, 
			String searchWord,
			Pageable pageable) {
		
	    return productRepository.findByConditions(bigId, 
	    		middleId, 
	    		smallId, 
	    		minCost, 
	    		maxCost,
	    		productSort, 
	    		productDiscount, 
	    		sellingResult, 
	    		searchWord,
	    		pageable);
	}
	
	 public String getProductPrice(Product product) {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String role = getRole(authentication);

	        if (product.getProductPriceTarget() == 0) {
	            return formatPrice(product.getProductPrice(), product.getProductDiscountSign(), product.getProductNoneDiscount());
	        } else if (product.getProductPriceTarget() == 1) {
	            if ("ROLE_MEMBER".equals(role) || "ROLE_DEALER".equals(role)) {
	                return formatPrice(product.getProductPrice(), product.getProductDiscountSign(), product.getProductMemberDiscount());
	            }
	        } else if (product.getProductPriceTarget() == 2) {
	            if ("ROLE_DEALER".equals(role)) {
	                return formatPrice(product.getProductPrice(), product.getProductDiscountSign(), product.getProductDealerDiscount());
	            }
	        }
	        return "전화문의";
	    }

    private String getRole(Authentication authentication) {
        if (authentication != null && authentication.getAuthorities() != null) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                return authority.getAuthority();
            }
        }
        return null;
    }

    private String formatPrice(int productPrice, Boolean productDiscountSign, int discountPrice) {
        if (Boolean.TRUE.equals(productDiscountSign)) {
            return String.format("%d 원", discountPrice);
        }
        return String.format("%d 원", productPrice);
    }
    
    public Product findProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findProductsByIds(List<Long> ids) {
        return productRepository.findAllById(ids);
    }
    
    public void updateProductQuantity(Long id, int quantity) {
        cart.put(id, quantity);
    }

    public void removeProductFromCart(Long id) {
        cart.remove(id);
    }

    public int getTotalPrice(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        return product.getProductPrice() * cart.get(productId);
    }

    public CartSummary calculateCartSummary() {
        int totalPrice = cart.entrySet().stream()
            .mapToInt(entry -> {
                Product product = productRepository.findById(entry.getKey()).orElseThrow();
                return product.getProductPrice() * entry.getValue();
            })
            .sum();
        int taxPrice = (int) (totalPrice * 0.1);
        int shippingCost = calculateShippingCost();
        int finalPrice = totalPrice + taxPrice + shippingCost;
        return new CartSummary(totalPrice, shippingCost, taxPrice, finalPrice);
    }

    private int calculateShippingCost() {
        // 배송비 계산 로직
        return 5000; // 예시로 5000원으로 설정
    }

    public List<Product> getCartProducts() {
        return productRepository.findAllById(cart.keySet());
    }

    public Map<Long, Integer> getCartQuantities() {
        return new HashMap<>(cart);
    }
}


























