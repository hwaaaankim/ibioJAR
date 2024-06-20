package com.dev.IBIOECommerceJAR.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="tb_product_tag")
@Data
public class ProductTag {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PRODUCT_TAG_ID")
	private Long id;
	
	@Column(name="PRODUCT_ID")
	private Long productId;
	
	@Column(name="PRODUCT_TAG_TEXT")
	private String productTagText;
}
