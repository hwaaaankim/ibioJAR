package com.dev.IBIOECommerceJAR.model.authentication;

import java.util.Date;
import java.util.List;

import org.springframework.lang.Nullable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "TB_MEMBER")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "MEMBER_ID")
	private Long id;
	
	@Column(name = "MEMBER_USERNAME")
	private String username;
	
	@Column(name = "MEMBER_PASSWORD")
	private String password;
	
	@Column(name="MEMBER_NAME")
	private String name;
	
	@Column(name="MEMBER_BUSINESS")
	@Nullable
	private String business;
	
	@Column(name="MEMBER_EMAIL")
	private String email;
	
	@Column(name="MEMBER_PHONE")
	private String phone;
	
	@Column(name="MEMBER_FAX")
	@Nullable
	private String fax;
	
	@Column(name="MEMBER_BUSINESS_CODE")
	@Nullable
	private String businessCode;
	
	@Column(name="MEMBER_TELEPHONE")
	@Nullable
	private String telephone;
	
	@Column(name="MEMBER_ADDRESS")
	private String address;
	
	@Column(name="MEMBER_POSTAL")
	@Nullable
	private String postal;
	
	@Column(name="MEMBER_DELIVERY_ADDRESS")
	@Nullable
	private String deliveryAddress;
	
	@Column(name="MEMBER_DELIVERY_POSTAL")
	@Nullable
	private String deliveryPostal;
	
	@Column(name = "MEMBER_ROLE")
	private String role;
	
	@Column(name="MEMBER_ENABLED")
	private Boolean enabled;
	
	@Column(name="MEMBER_EXPIRED")
	private Boolean expired;
	
	@Column(name="MEMBER_JOIN_DATE")
	private Date joinDate;
	
	@Column(name="MEMBER_CHANGE_DATE")
	private Date changeDate;
	
	@Column(name="MEMBER_BUSINESS_FILE_ORIGINAL_NAME")
	private String businessOriginalName;
	
	@Column(name="MEMBER_BUSINESS_FILE_PATH")
	private String businessFilePath;

	@Column(name="MEMBER_BUSINESS_FILE_NAME")
	private String businessFileName;
	
	@Column(name="MEMBER_BUSINESS_FILE_EXTENSION")
	private String businessFileExtension;
	
	@Column(name="MEMBER_BUSINESS_FILE_ROAD")
	private String businessFileRoad;
	
	@Column(name="MEMBER_ACCOUNT_FILE_ORIGINAL_NAME")
	private String accountOriginalName;
	
	@Column(name="MEMBER_ACCOUNT_FILE_PATH")
	private String accountFilePath;

	@Column(name="MEMBER_ACCOUNT_FILE_NAME")
	private String accountFileName;
	
	@Column(name="MEMBER_ACCOUNT_FILE_EXTENSION")
	private String accountFileExtension;
	
	@Column(name="MEMBER_ACCOUNT_FILE_ROAD")
	private String accountFileRoad;
	
	@OneToMany(
			fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			mappedBy = "memberId"
			)
	private List<MemberFile> files;
	
}


























