package com.dev.IBIOECommerceJAR.service.authentication;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dev.IBIOECommerceJAR.dto.MemberDTO;
import com.dev.IBIOECommerceJAR.dto.SignUpDTO;
import com.dev.IBIOECommerceJAR.model.authentication.Member;
import com.dev.IBIOECommerceJAR.model.authentication.MemberFile;
import com.dev.IBIOECommerceJAR.repository.MemberFileRepository;
import com.dev.IBIOECommerceJAR.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Service
@Slf4j
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private MemberFileRepository memberFileRepository;

	@Value("${spring.upload.env}")
	private String env;

	@Value("${spring.upload.path}")
	private String commonPath;

	@Bean(name = "saveBean")
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public void deleteRegistartion(MemberDTO dto) {
		memberRepository.deleteById(dto.getId());
	}
	
	public Member changeStatus(MemberDTO dto) {
		
		memberRepository.findById(dto.getId()).ifPresent(m->{
			m.setDiscount(dto.getAddedDiscount());
			m.setCalculate(dto.getCaculate());
			m.setCommission(dto.getCommission());
			m.setEnabled(true);
			memberRepository.save(m);
		});
		
		return memberRepository.findById(dto.getId()).get();
	}
	
	public Page<Member> findByDate(Pageable pageable, String startDate, String endDate) throws ParseException {

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat bf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if ("".equals(startDate) && "".equals(startDate)) {

			Date today = new Date();
			String day = bf.format(today);

			String start = day.substring(0, 10) + " 00:00:00";
			String end = day.substring(0, 10) + " 23:59:00";

			Date first = bf.parse(start);
			Date second = bf.parse(end);
			return memberRepository.findAllByEnabledAndJoindateBetween(pageable, false, first, second);

		} else if (!"".equals(startDate) && !"".equals(startDate) && startDate.equals(endDate)) {
			String start = startDate.substring(0, 10) + " 00:00:00";
			Date first = f.parse(start);
			Date second = f.parse(start);

			Calendar c = Calendar.getInstance();
			c.setTime(second);
			c.add(Calendar.DATE, 1);
			second = c.getTime();

			return memberRepository.findAllByEnabledAndJoindateBetween(pageable, false, first, second);

		} else if ("".equals(startDate) && !"".equals(endDate)) {

			Date second = f.parse(endDate);
			return memberRepository.findAllByEnabledAndJoindateLessThan(pageable, false, second);

		} else if (!"".equals(startDate) && "".equals(endDate)) {
			Date first = f.parse(startDate);
			return memberRepository.findAllByEnabledAndJoindateGreaterThan(pageable, false, first);
		} else {
			Date first = f.parse(startDate);
			Date second = f.parse(endDate);

			Calendar c = Calendar.getInstance();
			c.setTime(second);
			c.add(Calendar.DATE, 1);
			second = c.getTime();

			return memberRepository.findAllByEnabledAndJoindateBetween(pageable, false, first, second);
		}
	}
	
	public Member insertDealer(
			SignUpDTO dto, 
			List<MultipartFile> dealerFiles
			) throws IllegalStateException, IOException {

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
		member.setJoindate(new Date());
		member.setChangeDate(new Date());

		Member newMember = memberRepository.save(member);
		
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
		if(dealerFiles != null) {
			for(MultipartFile f : dealerFiles) {
				if(!f.isEmpty()) {
					String contentType = f.getContentType();
					String fileExtension = "";
					
					if (ObjectUtils.isEmpty(contentType)) {
						return null;
					} else {
						if (contentType.contains("image/jpeg")) {
							fileExtension = ".jpg";
						} else if (contentType.contains("image/png")) {
							fileExtension = ".png";
						} else if (contentType.contains("image/gif")) {
							fileExtension = ".gif";
						} else if (contentType.contains("application/pdf")) {
							fileExtension = ".pdf";
						} else if (contentType.contains("application/x-zip-compressed")) {
							fileExtension = ".zip";
						} else if (contentType
								.contains("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
							fileExtension = ".xlsx";
						} else if (contentType
								.contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
							fileExtension = ".docx";
						} else if (contentType.contains("text/plain")) {
							fileExtension = ".txt";
						} else if (contentType.contains("image/x-icon")) {
							fileExtension = ".ico";
						} else if (contentType.contains("application/haansofthwp")) {
							fileExtension = ".hwp";
						}
					}
					
					String originalName = f.getOriginalFilename();
					String fileName = accountGeneratedString + "." + fileExtension;
					String filePath = path + dto.getUsername() + "/account/" + fileName;
					String fileRoad = road + dto.getUsername() + "/account/" + fileName;
					String memberFileExtension = fileExtension;
					
					String accountSavePath = filePath;
					File accountSaveFile = new File(accountSavePath);	
					if (!accountSaveFile.exists()) {
						accountSaveFile.mkdirs();
					}
					f.transferTo(accountSaveFile);
					
					MemberFile memberFile = new MemberFile();
					memberFile.setMemberFileOriginalName(originalName);
					memberFile.setMemberFileDate(new Date());
					memberFile.setMemberFilePath(accountSavePath);
					memberFile.setMemberFileName(fileName);
					memberFile.setMemberFileRoad(fileRoad);
					memberFile.setMemberFileExtentsion(memberFileExtension);
					memberFile.setMemberId(newMember.getId());
					
					memberFileRepository.save(memberFile);
					
				}
			}
		}
		return newMember;
	}
	
	public Member insertMember(
			SignUpDTO dto, 
			List<MultipartFile> memberFiles
			) throws IllegalStateException, IOException {

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
		member.setRole("ROLE_MEMBER");
		member.setEnabled(false);
		member.setExpired(true);
		member.setJoindate(new Date());
		member.setChangeDate(new Date());

		Member newMember = memberRepository.save(member);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String current_date = simpleDateFormat.format(new Date());
		
		// 실제 파일 저장 위치
		String path = commonPath + "member/" + current_date + "/";
		// 파일 resource 로드 url
		String road = "/upload/member/" + current_date + "/";
		
		int leftLimit = 48;
		int rightLimit = 122;
		int targetStringLength = 10;
		Random random = new Random();

		// Account File 처리
		String accountGeneratedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		if(memberFiles != null) {
			for(MultipartFile f : memberFiles) {
				if(!f.isEmpty()) {
					String contentType = f.getContentType();
					String fileExtension = "";
					
					if (ObjectUtils.isEmpty(contentType)) {
						return null;
					} else {
						if (contentType.contains("image/jpeg")) {
							fileExtension = ".jpg";
						} else if (contentType.contains("image/png")) {
							fileExtension = ".png";
						} else if (contentType.contains("image/gif")) {
							fileExtension = ".gif";
						} else if (contentType.contains("application/pdf")) {
							fileExtension = ".pdf";
						} else if (contentType.contains("application/x-zip-compressed")) {
							fileExtension = ".zip";
						} else if (contentType
								.contains("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
							fileExtension = ".xlsx";
						} else if (contentType
								.contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
							fileExtension = ".docx";
						} else if (contentType.contains("text/plain")) {
							fileExtension = ".txt";
						} else if (contentType.contains("image/x-icon")) {
							fileExtension = ".ico";
						} else if (contentType.contains("application/haansofthwp")) {
							fileExtension = ".hwp";
						}
					}
					
					String originalName = f.getOriginalFilename();
					String fileName = accountGeneratedString + "." + fileExtension;
					String filePath = path + dto.getUsername() + "/account/" + fileName;
					String fileRoad = road + dto.getUsername() + "/account/" + fileName;
					String memberFileExtension = fileExtension;
					
					String accountSavePath = filePath;
					File accountSaveFile = new File(accountSavePath);	
					if (!accountSaveFile.exists()) {
						accountSaveFile.mkdirs();
					}
					f.transferTo(accountSaveFile);
					
					MemberFile memberFile = new MemberFile();
					memberFile.setMemberFileOriginalName(originalName);
					memberFile.setMemberFileDate(new Date());
					memberFile.setMemberFilePath(accountSavePath);
					memberFile.setMemberFileName(fileName);
					memberFile.setMemberFileRoad(fileRoad);
					memberFile.setMemberFileExtentsion(memberFileExtension);
					memberFile.setMemberId(newMember.getId());
					
					memberFileRepository.save(memberFile);
					
				}
			}
		}

		return newMember;
	}
	
	
	public Member registration(SignUpDTO dto, List<MultipartFile> memberFile) throws IllegalStateException, IOException {
		if(dto.getSign().equals("dealer")) {
			return insertDealer(dto, memberFile);
		}else{
			return insertMember(dto, memberFile);
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
