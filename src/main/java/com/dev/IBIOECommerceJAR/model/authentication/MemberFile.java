package com.dev.IBIOECommerceJAR.model.authentication;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="TB_MEMBER_FILE")
@Data
public class MemberFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="MEMBER_FILE_ID")
	private Long id;
	
	@Column(name="MEMBER_FILE_NAME")
	private String memberFileName;
	
	@Column(name="MEMBER_FILE_PATH")
	private String memberFilePath;
	
	@Column(name="MEMBER_FILE_ROAD")
	private String memberFileRoad;
	
	@Column(name="MEMBER_FILE_DATE")
	private Date memberFileDate;
	
	@Column(name="MEMBER_FILE_EXTENSION")
	private String memberFileExtentsion;
	
	@Column(name="MEMBER_REFER_ID")
	private Long memberId;
}
