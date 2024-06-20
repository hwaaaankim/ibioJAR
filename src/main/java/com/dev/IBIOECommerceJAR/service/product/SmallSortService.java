package com.dev.IBIOECommerceJAR.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.IBIOECommerceJAR.model.product.MiddleSort;
import com.dev.IBIOECommerceJAR.model.product.SmallSort;
import com.dev.IBIOECommerceJAR.repository.product.SmallSortRepository;

@Service
public class SmallSortService {

	@Autowired
	SmallSortRepository smallSortRepository;
	
	public void insertMiddleSort(
			String smallSorts, 
			MiddleSort middleSort
			) {
		String arr[] = smallSorts.split(",");
		for(String small : arr) {
			int index = 1;
			SmallSort smallSort = new SmallSort();
			if(smallSortRepository.findFirstIndex().isPresent()) {
				index = smallSortRepository.findFirstIndex().get() + 1;
			}
			smallSort.setSmallSortIndex(index);
			smallSort.setName(small);
			smallSort.setMiddleSort(middleSort);
			smallSortRepository.save(smallSort);
		}
		
	}
}
