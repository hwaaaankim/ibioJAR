package com.dev.IBIOECommerceJAR.dto.bankda;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class ResultDTO {

	private String return_code;
	private String description;
	private List<Map<String, String>> orders;
}
