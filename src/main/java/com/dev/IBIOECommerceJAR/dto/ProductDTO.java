package com.dev.IBIOECommerceJAR.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	private Long bigId;
	private Long middleId;
	private Long smallId;
	private String brand;
	private MultipartFile brandImage;
	private String title;
	private String productSpecs;
	private String productTags;
	private String description;
	private MultipartFile productImage;
	private List<MultipartFile> slideImages;
	private Integer price;
	private Integer priceTarget;
	private Integer noneD;
	private Integer memberD;
	private Integer dealerD;
	
	private String[] noneChangeOptionName;
	private String[] noneChangeOptionValues;
	private String[] changeOptionName;
	private String[] changeOptionValues;
	
	private Boolean eventSign;
	private String eventN;
	private Integer eventNoneD;
	private Integer eventMemberD;
	private Integer eventDealerD;
	
	private MultipartFile specImage;
	private List<MultipartFile> addedFiles;
	
}
