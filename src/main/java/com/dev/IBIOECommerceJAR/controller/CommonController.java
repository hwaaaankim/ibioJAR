package com.dev.IBIOECommerceJAR.controller;

import java.io.IOException;
import java.util.List;

import org.apache.commons.codec.EncoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dev.IBIOECommerceJAR.dto.SignUpDTO;
import com.dev.IBIOECommerceJAR.model.authentication.Member;
import com.dev.IBIOECommerceJAR.repository.MemberRepository;
import com.dev.IBIOECommerceJAR.service.SMSService;
import com.dev.IBIOECommerceJAR.service.authentication.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CommonController {

	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	SMSService smsService;
	
	@GetMapping({"/", "/index"})
	public String index(
			Model model
			) {
		log.info("index 접속");
//		String absolutePath = new File("").getAbsolutePath() + "\\";
//		Optional<Member> member = memberRepository.findByUsername("dealer_test1");
//		model.addAttribute("member", member.get());
		return "front/common/index";
	}
	
	@GetMapping("/loginForm")
	public String loginForm(
			Model model
			) {
		
		return "front/common/login";
	}
	
	@PostMapping("/signinProcess")
	public String loginProcess() {
		
		return "front/common/index";
	}
	
	@GetMapping("/signupForm")
	public String signupForm() {
		
		return "front/common/signupForm";
	}
	
	@PostMapping("/usernameCheck")
	@ResponseBody
	public Boolean usernameCheck(
			String username
			) {
		
		return memberRepository.findByUsername(username).isPresent();
	}
	
	@PostMapping("/phoneCheck")
	@ResponseBody
	public Boolean phoneCheck(
			String phone
			) {
		
		return memberRepository.findByPhone(phone).isPresent();
	}
	
	@PostMapping("/emailCheck")
	@ResponseBody
	public Boolean emailCheck(
			String email
			) {
		System.out.println(email);
		return memberRepository.findByEmail(email).isPresent();
	}
	
//	@PostMapping("/registration")
//	@ResponseBody
//	public String registration(
//			SignUpDTO dto
//			) {
//		
//		if(memberService.insertMember(dto)!=null) {
//			String msg = "회원 가입이 완료 되었습니다.";
//			StringBuilder sb = new StringBuilder();
//			sb.append("alert('"+msg+"');");
//			sb.append("location.href='/index'");
//			sb.insert(0, "<script>");
//			sb.append("</script>");
//			return sb.toString();
//		}else {
//			String msg = "에러가 발생 하였습니다. 다시 시도해 주세요.";
//			StringBuilder sb = new StringBuilder();
//			sb.append("alert('"+msg+"');");
//			sb.append("location.href='/signupForm'");
//			sb.insert(0, "<script>");
//			sb.append("</script>");
//			
//			return sb.toString();
//		}
//	}
	
//	@PostMapping("/dealerRegistration")
//	@ResponseBody
//	public String dealerRegistration(
//			SignUpDTO dto,
//			MultipartFile accountFile,
//			MultipartFile businessFile
//			) throws IllegalStateException, IOException {
//		System.out.println(dto);
//		System.out.println(businessFile.isEmpty());
//		System.out.println(accountFile.isEmpty());
//		if(memberService.insertDealer(dto, accountFile, businessFile)!=null) {
//			String msg = "딜러 회원가입이 완료 되었습니다. 관리자 승인 후 이용 가능합니다.";
//			StringBuilder sb = new StringBuilder();
//			sb.append("alert('"+msg+"');");
//			sb.append("location.href='/index'");
//			sb.insert(0, "<script>");
//			sb.append("</script>");
//			return sb.toString();
//		}else {
//			String msg = "에러가 발생 하였습니다. 다시 시도해 주세요.";
//			StringBuilder sb = new StringBuilder();
//			sb.append("alert('"+msg+"');");
//			sb.append("location.href='/dealerSignupForm'");
//			sb.insert(0, "<script>");
//			sb.append("</script>");
//			
//			return sb.toString();
//		}
//	}
	
	@PostMapping("/registration")
	@ResponseBody
	public String registration(
			SignUpDTO dto,
			@RequestBody(required = false) List<MultipartFile> memberFile

			) throws IllegalStateException, IOException, EncoderException {
	
		Member newMember = memberService.registration(dto, memberFile);
		smsService.sendMessage(newMember.getPhone(), "회원 가입 신청이 완료 되었습니다. 관리자의 승인 시 알림 메시지가 발송되며 쇼핑몰 이용이 가능합니다.");
		String adminMsg = "";
		if(dto.getSign().equals("dealer")) {
			adminMsg = "딜러 회원 가입신청이 발생 하였습니다.";
		}else {
			adminMsg = "일반 회원 가입신청이 발생 하였습니다.";
		}
		smsService.sendMessage("010-3894-3849", adminMsg);
		String msg = "회원 가입이 완료 되었습니다.";
		StringBuilder sb = new StringBuilder();
		sb.append("alert('"+msg+"');");
		sb.append("location.href='/login'");
		sb.insert(0, "<script>");
		sb.append("</script>");
		return sb.toString();
		
	}
	
	@GetMapping("/dealerSignupForm")
	public String dealerSignupForm() {
		
		return "front/common/dealer/signupForm";
	}
	
	@GetMapping("/notice")
	public String notice() {
		
		return "front/common/notice/notice";
	}
	
	@GetMapping("/faq")
	public String faq() {
		
		return "front/common/faq";
	}
	
	@GetMapping("/contact")
	public String contact() {
		
		return "front/common/contact";
	}
	
	@GetMapping("/privacy")
	public String privacy() {
		
		return "front/common/privacy";
	}
	
	@GetMapping("/policy")
	public String policy() {
		
		return "front/common/policy";
	}
	
	@GetMapping("/sitemap")
	public String sitemap() {
		
		return "front/common/sitemap";
	}
	
	@GetMapping("/about")
	public String about() {
		
		return "front/common/about";
	}
	
	@GetMapping("/event")
	public String event() {
		
		return "front/common/event/event";
	}
	
	@GetMapping("/findPassword")
	public String findPassword() {
		
		return "front/common/info/findPassword";
	}
	
	@GetMapping("/findUsername")
	public String findUsername() {
		
		return "front/common/info/findUsername";
	}
	
	@GetMapping("/dealerShop")
	public String dealerShop() {
		
		return "front/common/dealer/index";
	}
	
	@GetMapping("/noticeDetail")
	public String noticeDetail() {
		
		return "front/common/notice/noticeDetail";
	}
	
	@GetMapping("/eventDetail")
	public String eventDetail() {
		
		return "front/common/event/eventDetail";
	}
	
	@GetMapping("/productCompare")
	public String productCompare() {
		
		return "front/common/product/productCompare";
	}
	
	
	
	
	
	
	
	
}
