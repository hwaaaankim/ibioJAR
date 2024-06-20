package com.dev.IBIOECommerceJAR.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.IBIOECommerceJAR.model.product.BigSort;
import com.dev.IBIOECommerceJAR.repository.product.BigSortRepository;

@Service
public class BigSortService {

	@Autowired
	BigSortRepository bigSortRepository;
	
	public void insertBigSort(String bigSorts) {
		String arr[] = bigSorts.split(",");
		for(String big : arr) {
			int index = 1;
			BigSort bigSort = new BigSort();
			if(bigSortRepository.findFirstIndex().isPresent()) {
				index = bigSortRepository.findFirstIndex().get() + 1;
			}
			bigSort.setBigSortIndex(index);
			bigSort.setName(big);
			bigSortRepository.save(bigSort);
		}
		
	}
	
}
