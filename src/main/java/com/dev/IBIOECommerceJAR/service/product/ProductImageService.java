package com.dev.IBIOECommerceJAR.service.product;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dev.IBIOECommerceJAR.model.product.Product;
import com.dev.IBIOECommerceJAR.model.product.ProductImage;
import com.dev.IBIOECommerceJAR.repository.product.ProductImageRepository;

@Service
public class ProductImageService {
	
	@Autowired
	ProductImageRepository productImageRepository;
	
	@Value("${spring.upload.env}")
	private String env;
	
	@Value("${spring.upload.path}")
	private String commonPath;
	
	public String imageUpload(
			Product product,
			List<MultipartFile> images
			) throws IllegalStateException, IOException {
	
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());

        // 실제 파일 저장 위치
 		String path = commonPath + "product/ibio/" + current_date + "/";
 		// 파일 resource 로드 url
 		String road = "/upload/product/ibio/" + current_date + "/";
        
        int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();
		
        for(MultipartFile file : images) {
        	if(!file.isEmpty()) {
        		String generatedString = random.ints(leftLimit,rightLimit + 1)
      				  .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
      				  .limit(targetStringLength)
      				  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
      				  .toString();
        		ProductImage f = new ProductImage();
        		f.setProductId(product.getId());
            	String contentType = file.getContentType();
                String originalFileExtension = "";
                // 확장자 명이 없으면 이 파일은 잘 못 된 것이다
                if (ObjectUtils.isEmpty(contentType)){
                    return "NONE";
                }else {
        			if (contentType.contains("image/jpeg")) {
        				originalFileExtension = ".jpg";
        			} else if (contentType.contains("image/png")) {
        				originalFileExtension = ".png";
        			} 
                }
                String productImageName = generatedString + originalFileExtension;
				String productImagePath = path + product.getProductCode() + "/slides/" + productImageName;
				String productImageRoad = road + product.getProductCode() + "/slides/" + productImageName;
				String productImageSavePath = productImagePath;
				File productImageSaveFile = new File(productImageSavePath);	
				if (!productImageSaveFile.exists()) {
					productImageSaveFile.mkdirs();
				}
                file.transferTo(productImageSaveFile);
                f.setProductImageOriginalName(file.getOriginalFilename());
                f.setProductImageExtension(originalFileExtension);
                f.setProductImagePath(productImagePath);
                f.setProductImageRoad(productImageRoad);
                f.setProductImageName(productImageName);
                f.setProductImageDate(new Date());
                productImageRepository.save(f);
            }
        }
        
        return "success";
	}
	
	public Boolean imageDelete(
			Long id
			) {
		List<ProductImage> images = productImageRepository.findAllByProductId(id);
		for(ProductImage i : images) {
			File slide = new File(i.getProductImagePath());
			if(!slide.delete()) {
				return false;
			}
		}
		
		return true;
	}

}
