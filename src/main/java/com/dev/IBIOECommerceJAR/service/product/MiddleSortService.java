package com.dev.IBIOECommerceJAR.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.IBIOECommerceJAR.model.product.BigSort;
import com.dev.IBIOECommerceJAR.model.product.MiddleSort;
import com.dev.IBIOECommerceJAR.repository.product.MiddleSortRepository;

@Service
public class MiddleSortService {

	@Autowired
	MiddleSortRepository middleSortRepository;
	
	public void insertMiddleSort(
			String middleSorts, 
			BigSort bigSort
			) {
		String arr[] = middleSorts.split(",");
		for(String middle : arr) {
			int index = 1;
			MiddleSort middleSort = new MiddleSort();
			if(middleSortRepository.findFirstIndex().isPresent()) {
				index = middleSortRepository.findFirstIndex().get() + 1;
			}
			middleSort.setMiddleSortIndex(index);
			middleSort.setName(middle);
			middleSort.setBigSort(bigSort);
			middleSortRepository.save(middleSort);
		}
		
	}
}
