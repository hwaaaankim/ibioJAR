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
import com.dev.IBIOECommerceJAR.model.product.ProductOption;
import com.dev.IBIOECommerceJAR.repository.product.ProductOptionRepository;

@Service
public class ProductOptionService {

	@Autowired
	ProductOptionRepository productOptionRepository;

	@Value("${spring.upload.path}")
	private String commonPath;
	
	public void insertChangeOption(
			Product product, 
			String[] optionName, 
			String[] optionValue,
			String[] optionUnits,
			Integer[] optionPrices,
			String[] optionSigns) {
		for (int a = 0; a < optionName.length; a++) {
			
			if (optionName[a] != null 
					&& !optionName[a].equals("") 
					&& optionValue[a] != null
					&& !optionValue[a].equals("")) {
				String sign = optionSigns[a];
				ProductOption option = new ProductOption();
				if(sign.equals("+")) {
					option.setProductOptionPriceSign(true);
				}else {
					option.setProductOptionPriceSign(false);
				}
				option.setProductId(product.getId());
				option.setCode(true);
				option.setProductOptionName(optionName[a]);
				option.setProductOptionValue(optionValue[a]);
				option.setProductOptionUnit(optionUnits[a]);
				option.setProductOptionPrice(optionPrices[a]);
				option.setProductOptionFileName("-");
				option.setProductOptionFilePath("-");
				option.setProductOptionFileRoad("-");
				productOptionRepository.save(option);
			}
		}
	}

	public void insertNoneChangeOption(
			Product product, 
			String[] optionName, 
			String[] optionValue,
			List<MultipartFile> optionFiles) throws IllegalStateException, IOException {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String current_date = simpleDateFormat.format(new Date());

		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();
		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		String productCode = product.getProductCode();
		// 실제 파일 저장 위치
		String path = commonPath + "product/ibio/" + current_date + "/";
		// 파일 resource 로드 url
		String road = "/upload/product/ibio/" + current_date + "/";
		
		for (int a = 0; a < optionName.length; a++) {
			if (optionName[a] != null 
					&& !optionName[a].equals("") 
					&& optionValue[a] != null
					&& !optionValue[a].equals("")
					&& !optionFiles.get(a).isEmpty()
					) {
					ProductOption option = new ProductOption();
					
					String optionImageContentType = optionFiles.get(a).getContentType();
					String optionImageFileExtension = "";
					if (ObjectUtils.isEmpty(optionImageContentType)) {
					} else {
						if (optionImageContentType.contains("image/jpeg")) {
							optionImageFileExtension = ".jpg";
						} else if (optionImageContentType.contains("image/png")) {
							optionImageFileExtension = ".png";
						} 
					}
					String optionImageName = generatedString + optionImageFileExtension;
					String optionImagePath = path + productCode + "/option/" + optionImageName;
					String optionImageRoad = road + productCode + "/option/" + optionImageName;
					
					String optionImageSavePath = optionImagePath;
					File optionImageSaveFile = new File(optionImageSavePath);	
					if (!optionImageSaveFile.exists()) {
						optionImageSaveFile.mkdirs();
					}
					optionFiles.get(a).transferTo(optionImageSaveFile);
					
					option.setProductId(product.getId());
					option.setCode(false);
					option.setProductOptionName(optionName[a]);
					option.setProductOptionValue(optionValue[a]);
					option.setProductOptionUnit("-");
					option.setProductOptionPrice(0);
					option.setProductOptionFileName(optionImageName);
					option.setProductOptionFileRoad(optionImageRoad);
					option.setProductOptionFilePath(optionImagePath);
					option.setProductOptionPriceSign(false);
					
					productOptionRepository.save(option);
			}
		}
	}
}





















