package com.dev.IBIOECommerceJAR.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.IBIOECommerceJAR.model.product.Product;
import com.dev.IBIOECommerceJAR.model.product.ProductOption;
import com.dev.IBIOECommerceJAR.repository.product.ProductOptionRepository;

@Service
public class ProductOptionService {

	@Autowired
	ProductOptionRepository productOptionRepository;

	public void insertChangeOption(
			Product product, 
			String[] optionName, 
			String[] optionValue) {
		for (int a = 0; a < optionName.length; a++) {
			
			if (optionName[a] != null 
					&& !optionName[a].equals("") 
					&& optionValue[a] != null
					&& !optionValue[a].equals("")) {
				String values[] = optionValue[a].split(",");
				for (int b = 0; b < values.length; b++) {
					int num = values[b].indexOf(":");
					String sign = values[b].substring(num + 1, num + 2);
					ProductOption option = new ProductOption();
					if(sign.equals("+")) {
						option.setProductOptionPriceSign(true);
					}else {
						option.setProductOptionPriceSign(false);
					}
					option.setProductId(product.getId());
					option.setCode(true);
					option.setProductOptionName(optionName[a]);
					option.setProductOptionValue(values[b]);
					option.setProductOptionPrice(Integer.parseInt(values[b].substring(num + 2)));
					productOptionRepository.save(option);
				}
			}
		}
	}

	public void insertNoneChangeOption(
			Product product, 
			String[] optionName, 
			String[] optionValue) {
		
		for (int a = 0; a < optionName.length; a++) {
			if (optionName[a] != null 
					&& !optionName[a].equals("") 
					&& optionValue[a] != null
					&& !optionValue[a].equals("")) {
				String values[] = optionValue[a].split(",");
				for (int b = 0; b < values.length; b++) {
					ProductOption option = new ProductOption();
					option.setProductId(product.getId());
					option.setCode(false);
					option.setProductOptionName(optionName[a]);
					option.setProductOptionValue(values[b]);
					option.setProductOptionPrice(0);
					productOptionRepository.save(option);
				}
			}
		}
	}
}
