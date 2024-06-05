package com.dev.IBIOECommerceJAR.service.authentication;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dev.IBIOECommerceJAR.dto.SignUpDTO;
import com.dev.IBIOECommerceJAR.model.authentication.Member;
import com.dev.IBIOECommerceJAR.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Service
@Slf4j
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Value("${spring.upload.env}")
	private String env;

	@Value("${spring.upload.path}")
	private String commonPath;

	@Bean(name = "saveBean")
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public Member insertMember(SignUpDTO dto) {

		Member member = new Member();

		member.setUsername(dto.getUsername());
		member.setPassword(passwordEncoder().encode(dto.getPassword()));
		member.setName(dto.getName());
		member.setBusiness(dto.getBusiness());
		member.setBusinessCode(dto.getBusinessCode());
		member.setEmail(dto.getEmail());
		member.setPhone(dto.getPhone());
		member.setFax(dto.getFax());
		member.setTelephone(dto.getTelephone());
		member.setAddress(dto.getAddress());
		member.setPostal(dto.getPostal());
		member.setDeliveryAddress(dto.getDeliveryAddress());
		member.setDeliveryPostal(dto.getDeliveryPostal());
		member.setRole("ROLE_MEMBER");
		member.setEnabled(true);
		member.setExpired(true);
		member.setJoinDate(new Date());
		member.setChangeDate(new Date());

		return memberRepository.save(member);
	}

	public Member insertDealer(
			SignUpDTO dto, 
			MultipartFile accountFile, 
			MultipartFile businessFile
			) throws IllegalStateException, IOException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String current_date = simpleDateFormat.format(new Date());
		
		// 실제 파일 저장 위치
		String path = commonPath + "dealer/" + current_date + "/";
		// 파일 resource 로드 url
		String road = "/upload/dealer/" + current_date + "/";
		
		int leftLimit = 48;
		int rightLimit = 122;
		int targetStringLength = 10;
		Random random = new Random();

		// Account File 처리
		String accountGeneratedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		String accountContentType = accountFile.getContentType();
		String accountOriginalFileExtension = "";
		// 확장자 명이 없으면 이 파일은 잘 못 된 것.
		if (ObjectUtils.isEmpty(accountContentType)) {
			return null;
		} else {
			if (accountContentType.contains("image/jpeg")) {
				accountOriginalFileExtension = ".jpg";
			} else if (accountContentType.contains("image/png")) {
				accountOriginalFileExtension = ".png";
			} else if (accountContentType.contains("application/pdf")) {
				accountOriginalFileExtension = ".pdf";
			}
		}
		String accountOriginalName = accountFile.getOriginalFilename();
		String accountFileName = accountGeneratedString + "." + accountOriginalFileExtension;
		String accountFilePath = path + dto.getUsername() + "/account/" + accountFileName;
		String accountFileRoad = road + dto.getUsername() + "/account/" + accountFileName;
		String accountFileExtension = accountOriginalFileExtension;
		
		String accountSavePath = "";
		if(env.equals("local")) {
			accountSavePath = accountFilePath;
		}else if(env.equals("prod")) {
			accountSavePath = accountFilePath;
		}
		File accountSaveFile = new File(accountSavePath);	
		if (!accountSaveFile.exists()) {
			accountSaveFile.mkdirs();
		}
		accountFile.transferTo(accountSaveFile);
		
		// Business File 처리
		String businessGeneratedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		String businessContentType = accountFile.getContentType();
		String businessOriginalFileExtension = "";
		// 확장자 명이 없으면 이 파일은 잘 못 된 것이다
		if (ObjectUtils.isEmpty(businessContentType)) {
			return null;
		} else {
			if (businessContentType.contains("image/jpeg")) {
				businessOriginalFileExtension = ".jpg";
			} else if (businessContentType.contains("image/png")) {
				businessOriginalFileExtension = ".png";
			} else if (businessContentType.contains("image/gif")) {
				businessOriginalFileExtension = ".gif";
			} else if (businessContentType.contains("application/pdf")) {
				businessOriginalFileExtension = ".pdf";
			} else if (businessContentType.contains("application/x-zip-compressed")) {
				businessOriginalFileExtension = ".zip";
			} else if (businessContentType
					.contains("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
				businessOriginalFileExtension = ".xlsx";
			} else if (businessContentType
					.contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
				businessOriginalFileExtension = ".docx";
			} else if (businessContentType.contains("text/plain")) {
				businessOriginalFileExtension = ".txt";
			} else if (businessContentType.contains("image/x-icon")) {
				businessOriginalFileExtension = ".ico";
			} else if (businessContentType.contains("application/haansofthwp")) {
				businessOriginalFileExtension = ".hwp";
			}
		}
		String businessFileName = businessGeneratedString  + "." + businessOriginalFileExtension;
		String businessFilePath = path + dto.getUsername() + "/business/" + businessFileName;
		String businessFileRoad = road + dto.getUsername() + "/business/" + businessFileName;
		String businessFileExtension = businessOriginalFileExtension;
		String businessOriginalName = businessFile.getOriginalFilename();
		
		String businessSavePath = "";
		if(env.equals("local")) {
			businessSavePath = businessFilePath;
		}else if(env.equals("prod")) {
			businessSavePath = businessFilePath;
		}
		File businessSaveFile = new File(businessSavePath);	
		if (!businessSaveFile.exists()) {
			businessSaveFile.mkdirs();
		}
		businessFile.transferTo(businessSaveFile);
		
		
		// DB Entity 작성
		Member member = new Member();
		member.setUsername(dto.getUsername());
		member.setPassword(passwordEncoder().encode(dto.getPassword()));
		member.setName(dto.getName());
		member.setBusiness(dto.getBusiness());
		member.setBusinessCode(dto.getBusinessCode());
		member.setEmail(dto.getEmail());
		member.setPhone(dto.getPhone());
		member.setFax(dto.getFax());
		member.setTelephone(dto.getTelephone());
		member.setAddress(dto.getAddress());
		member.setPostal(dto.getPostal());
		member.setDeliveryAddress(dto.getDeliveryAddress());
		member.setDeliveryPostal(dto.getDeliveryPostal());
		member.setRole("ROLE_DEALER");
		member.setEnabled(false);
		member.setExpired(true);
		member.setJoinDate(new Date());
		member.setChangeDate(new Date());
		member.setBusinessFileExtension(businessFileExtension);
		member.setBusinessFileName(businessFileName);
		member.setBusinessFilePath(businessFilePath);
		member.setBusinessFileRoad(businessFileRoad);
		member.setBusinessOriginalName(businessOriginalName);
		
		member.setAccountFileExtension(accountFileExtension);
		member.setAccountFileName(accountFileName);
		member.setAccountFilePath(accountFilePath);
		member.setAccountFileRoad(accountFileRoad);
		member.setAccountOriginalName(accountOriginalName);

		return memberRepository.save(member);
	}
	
//	public Member registration(
//			SignUpDTO dto, 
//			List<MultipartFile> memberFile
//			) throws IllegalStateException, IOException {
//
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		String current_date = simpleDateFormat.format(new Date());
//		
//		// 실제 파일 저장 위치
//		String path = commonPath + "dealer/" + current_date + "/";
//		// 파일 resource 로드 url
//		String road = "/upload/dealer/" + current_date + "/";
//		
//		int leftLimit = 48;
//		int rightLimit = 122;
//		int targetStringLength = 10;
//		Random random = new Random();
//
//		// Account File 처리
//		String accountGeneratedString = random.ints(leftLimit, rightLimit + 1)
//				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
//				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
//
//		String accountContentType = accountFile.getContentType();
//		String accountOriginalFileExtension = "";
//		// 확장자 명이 없으면 이 파일은 잘 못 된 것.
//		if (ObjectUtils.isEmpty(accountContentType)) {
//			return null;
//		} else {
//			if (accountContentType.contains("image/jpeg")) {
//				accountOriginalFileExtension = ".jpg";
//			} else if (accountContentType.contains("image/png")) {
//				accountOriginalFileExtension = ".png";
//			} else if (accountContentType.contains("application/pdf")) {
//				accountOriginalFileExtension = ".pdf";
//			}
//		}
//		String accountOriginalName = accountFile.getOriginalFilename();
//		String accountFileName = accountGeneratedString + "." + accountOriginalFileExtension;
//		String accountFilePath = path + dto.getUsername() + "/account/" + accountFileName;
//		String accountFileRoad = road + dto.getUsername() + "/account/" + accountFileName;
//		String accountFileExtension = accountOriginalFileExtension;
//		
//		String accountSavePath = "";
//		if(env.equals("local")) {
//			accountSavePath = accountFilePath;
//		}else if(env.equals("prod")) {
//			accountSavePath = accountFilePath;
//		}
//		File accountSaveFile = new File(accountSavePath);	
//		if (!accountSaveFile.exists()) {
//			accountSaveFile.mkdirs();
//		}
//		accountFile.transferTo(accountSaveFile);
//		
//		// Business File 처리
//		String businessGeneratedString = random.ints(leftLimit, rightLimit + 1)
//				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
//				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
//
//		String businessContentType = accountFile.getContentType();
//		String businessOriginalFileExtension = "";
//		// 확장자 명이 없으면 이 파일은 잘 못 된 것이다
//		if (ObjectUtils.isEmpty(businessContentType)) {
//			return null;
//		} else {
//			if (businessContentType.contains("image/jpeg")) {
//				businessOriginalFileExtension = ".jpg";
//			} else if (businessContentType.contains("image/png")) {
//				businessOriginalFileExtension = ".png";
//			} else if (businessContentType.contains("image/gif")) {
//				businessOriginalFileExtension = ".gif";
//			} else if (businessContentType.contains("application/pdf")) {
//				businessOriginalFileExtension = ".pdf";
//			} else if (businessContentType.contains("application/x-zip-compressed")) {
//				businessOriginalFileExtension = ".zip";
//			} else if (businessContentType
//					.contains("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
//				businessOriginalFileExtension = ".xlsx";
//			} else if (businessContentType
//					.contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
//				businessOriginalFileExtension = ".docx";
//			} else if (businessContentType.contains("text/plain")) {
//				businessOriginalFileExtension = ".txt";
//			} else if (businessContentType.contains("image/x-icon")) {
//				businessOriginalFileExtension = ".ico";
//			} else if (businessContentType.contains("application/haansofthwp")) {
//				businessOriginalFileExtension = ".hwp";
//			}
//		}
//		String businessFileName = businessGeneratedString  + "." + businessOriginalFileExtension;
//		String businessFilePath = path + dto.getUsername() + "/business/" + businessFileName;
//		String businessFileRoad = road + dto.getUsername() + "/business/" + businessFileName;
//		String businessFileExtension = businessOriginalFileExtension;
//		String businessOriginalName = businessFile.getOriginalFilename();
//		
//		String businessSavePath = "";
//		if(env.equals("local")) {
//			businessSavePath = businessFilePath;
//		}else if(env.equals("prod")) {
//			businessSavePath = businessFilePath;
//		}
//		File businessSaveFile = new File(businessSavePath);	
//		if (!businessSaveFile.exists()) {
//			businessSaveFile.mkdirs();
//		}
//		businessFile.transferTo(businessSaveFile);
//		
//		
//		// DB Entity 작성
//		Member member = new Member();
//		member.setUsername(dto.getUsername());
//		member.setPassword(passwordEncoder().encode(dto.getPassword()));
//		member.setName(dto.getName());
//		member.setBusiness(dto.getBusiness());
//		member.setBusinessCode(dto.getBusinessCode());
//		member.setEmail(dto.getEmail());
//		member.setPhone(dto.getPhone());
//		member.setFax(dto.getFax());
//		member.setTelephone(dto.getTelephone());
//		member.setAddress(dto.getAddress());
//		member.setPostal(dto.getPostal());
//		member.setDeliveryAddress(dto.getDeliveryAddress());
//		member.setDeliveryPostal(dto.getDeliveryPostal());
//		member.setRole("ROLE_DEALER");
//		member.setEnabled(false);
//		member.setExpired(true);
//		member.setJoinDate(new Date());
//		member.setChangeDate(new Date());
//		member.setBusinessFileExtension(businessFileExtension);
//		member.setBusinessFileName(businessFileName);
//		member.setBusinessFilePath(businessFilePath);
//		member.setBusinessFileRoad(businessFileRoad);
//		member.setBusinessOriginalName(businessOriginalName);
//		
//		member.setAccountFileExtension(accountFileExtension);
//		member.setAccountFileName(accountFileName);
//		member.setAccountFilePath(accountFilePath);
//		member.setAccountFileRoad(accountFileRoad);
//		member.setAccountOriginalName(accountOriginalName);
//
//		return memberRepository.save(member);
//	}
	
	public String registration(SignUpDTO dto, List<MultipartFile> memberFile) {
		if(memberFile == null) {
			return "null";
		}else {
			return memberFile.toString();
		}
	}

	public Member insertAdmin(Member member) {
		log.info("NEW ADMIN");
		String encodedPassword = passwordEncoder().encode(member.getPassword());
		member.setPassword(encodedPassword);
		member.setRole("ROLE_" + member.getRole());

		return memberRepository.save(member);

	}
}
