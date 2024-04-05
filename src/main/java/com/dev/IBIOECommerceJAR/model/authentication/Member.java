package com.dev.IBIOECommerceJAR.model.authentication;

import java.io.Serializable;
import java.util.Date;

import org.springframework.lang.Nullable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_MEMBER")
public class Member implements Serializable{

	private static final long serialVersionUID = 1L;

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
	private int postal;
	
	@Column(name="MEMBER_DELIVERY_ADDRESS")
	@Nullable
	private String deliveryAddress;
	
	@Column(name="MEMBER_DELIVERY_POSTAL")
	@Nullable
	private int deliveryPostal;
	
	@Column(name = "MEMBER_ROLE")
	private String role;
	
	@Column(name="MEMBER_ENABLED")
	private Boolean enabled;
	
	@Column(name="MEMBER_JOIN_DATE")
	private Date joinDate;
	
	@Column(name="MEMBER_CHANGE_DATE")
	private Date changeDate;
	
	@Column(name="MEMBER_FILE")
	private String filePath;

	@Column(name="MEMBER_FILE_NAME")
	private String fileName;
	
	@Column(name="MEMBER_FILE_ROAD")
	private String fileRoad;
	
}


























