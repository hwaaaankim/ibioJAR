package com.dev.IBIOECommerceJAR.model.product;

import java.util.List;

import org.springframework.lang.Nullable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Table(name="tb_product")
@Data
public class Product {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PRODUCT_ID")
	private Long id;
	
	@Column(name="PRODUCT_CODE")
	@Nullable
	private String productCode;

	@Column(name="PRODUCT_TITLE")
	private String productTitle;
	
	@Column(name="PRODUCT_BRAND")
	private String productBrand;
	
	@Column(name="PRODUCT_BRAND_IMAGE_PATH")
	private String productBrandImagePath;
	
	@Column(name="PRODUCT_BRAND_IMAGE_NAME")
	private String productBrandImageName;
	
	@Column(name="PRODUCT_BRAND_IMAGE_ROAD")
	private String productBrandImageRoad;
	
	@Column(name="PRODUCT_DESCRIPTION")
	private String productDescription;
	
	@Column(name="PRODUCT_PRICE")
	private int productPrice;

	@Column(name="PRODUCT_PRICE_TARGET")
	private int productPriceTarget;
	
	@Column(name="PRODUCT_NONE_DISCOUNT")
	private int productNoneDiscount;
	
	@Column(name="PRODUCT_MEMBER_DISCOUNT")
	private int productMemberDiscount;
	
	@Column(name="PRODUCT_DEALER_DISCOUNT")
	private int productDealerDiscount;
	
	@Column(name="PRODUCT_IMAGE_PATH")
	private String productImagePath;
	
	@Column(name="PRODUCT_IMAGE_NAME")
	private String productImageName;
	
	@Column(name="PRODUCT_IMAGE_ROAD")
	private String productImageRoad;
	
	@Column(name="PRODUCT_SPEC_IMAGE_PATH")
	private String productSpecImagePath;
	
	@Column(name="PRODUCT_SPEC_IMAGE_NAME")
	private String productSpecImageName;
	
	@Column(name="PRODUCT_SPEC_IMAGE_ROAD")
	private String productSpecImageRoad;
	
	@Column(name="PRODUCT_EVENT_SIGN")
	private Boolean productEventSign;
	
	@Column(name="PRODUCT_EVENT_NAME")
	private String productEventName;
	
	@Column(name="PRODUCT_EVENT_NONE_DISCOUNT")
	private int productEventNoneDiscount;
	
	@Column(name="PRODUCT_EVENT_MEMBER_DISCOUNT")
	private int productEventMemberDiscount;
	
	@Column(name="PRODUCT_EVENT_Dealer_DISCOUNT")
	private int productEventDealerDiscount;
	
	
	@Column(name="PRODUCT_INDEX")
	private int productIndex;
	
	@Column(name="PRODUCT_SELL")
	private int productSell;
	
	@Column(name="PRODUCT_CLICK")
	private int productClicks;
	
	@Transient
	private Long smallId;
	
	@Transient
	private Long middleId;
	
	@Transient
	private Long bigId;
	
	@OneToMany(
			fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			mappedBy = "productId"
			)
	private List<ProductImage> images;
	
	@OneToMany(
			fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			mappedBy = "productId"
			)
	private List<ProductTag> tags;
	
	@OneToMany(
			fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			mappedBy = "productId"
			)
	private List<ProductFile> files;
	
	@OneToMany(
			fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			mappedBy = "productId"
			)
	private List<ProductSpec> specs;
	
	@OneToMany(
			fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			mappedBy = "productId"
			)
	private List<ProductOption> options;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(
			name="PRODUCT_REFER_ID", referencedColumnName="SMALL_SORT_ID"
			)
	private SmallSort smallSort;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(
			name="PRODUCT_MIDDLE_REFER_ID", referencedColumnName="MIDDLE_SORT_ID"
			)
	private MiddleSort middleSort;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(
			name="PRODUCT_BIG_REFER_ID", referencedColumnName="BIG_SORT_ID"
			)
	private BigSort bigSort;
	
}
























