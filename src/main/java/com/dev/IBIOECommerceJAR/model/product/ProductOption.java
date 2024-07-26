package com.dev.IBIOECommerceJAR.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="tb_product_option")
@Data
public class ProductOption {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PRODUCT_OPTION_ID")
	private Long id;
	
	@Column(name="PRODUCT_ID")
	private Long productId;
	
	// 가격변화 유무
	@Column(name="PRODUCT_OPTION_CODE")
	private Boolean code;
	
	@Column(name="PRODUCT_OPTION_NAME")
	private String productOptionName;
	
	@Column(name="PRODUCT_OPTION_VALUE")
	private String productOptionValue;

	@Column(name="PRODUCT_OPTION_UNIT")
	private String productOptionUnit;
	
	@Column(name="PRODUCT_OPTION_PRICE")
	private Integer productOptionPrice;
	
	@Column(name="PRODUCT_OPTION_FILE_NAME")
	private String productOptionFileName;
	
	@Column(name="PRODUCT_OPTION_FILE_PATH")
	private String productOptionFilePath;
	
	@Column(name="PRODUCT_OPTION_FILE_ROAD")
	private String productOptionFileRoad;
	
	// 가격변화 있는 경우 + 인지 - 인지
	@Column(name="PRODUCT_OPTION_PRICE_SIGN")
	private Boolean productOptionPriceSign;
	
	
}
