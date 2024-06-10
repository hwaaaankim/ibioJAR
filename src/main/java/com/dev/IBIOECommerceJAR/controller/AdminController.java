package com.dev.IBIOECommerceJAR.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dev.IBIOECommerceJAR.model.authentication.Member;
import com.dev.IBIOECommerceJAR.repository.MemberRepository;
import com.dev.IBIOECommerceJAR.service.authentication.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	MemberService memberService;
	
	@GetMapping({"" , "/", "/index"})
	public String adminIndex() {
		log.info("admin 접속");
		
		
		return "administration/common/index";
	} 
	
	@GetMapping("/memberRegistrationCheck")
	public String memberRegistrationCheck(
			Model model,
			@PageableDefault(size = 10) Pageable pageable,
			@RequestParam(required = false, defaultValue = "none") String searchType,
			@RequestParam(required = false) String searchWord,
			@RequestParam(required = false) String phoneNumber,
			@RequestParam(required = false) String startDate, 
			@RequestParam(required = false) String endDate
			) throws ParseException {
		
		Page<Member> members = null;
		
		if(searchType==null || "none".equals(searchType)) {
			members = memberRepository.findAllByEnabledOrderByJoindateDesc(pageable, false);
		}else if("name".equals(searchType)) {
			if(!"".equals(searchWord)) {
				members = memberRepository.findAllByEnabledAndNameContainingOrderByJoindateDesc(pageable, false, searchWord);
			}
		}else if("username".equals(searchType)) {
			if(!"".equals(searchWord)) {
				members = memberRepository.findAllByEnabledAndUsernameContainingOrderByJoindateDesc(pageable, false, searchWord);
			}
		}else if("phone".equals(searchType)) {
			if(!"".equals(phoneNumber)) {
				members = memberRepository.findAllByEnabledAndPhoneContainingOrderByJoindateDesc(pageable, false, phoneNumber);
			}
		}else if("email".equals(searchType)) {
			if(!"".equals(searchWord)) {
				members = memberRepository.findAllByEnabledAndEmailContainingOrderByJoindateDesc(pageable, false, searchWord);
			}
		}else if("business".equals(searchType)) {
			if(!"".equals(searchWord)) {
				members = memberRepository.findAllByEnabledAndBusinessContainingOrderByJoindateDesc(pageable, false, searchWord);
			}
		}else if("period".equals(searchType)) {
			members = memberService.findByDate(pageable, startDate, endDate);
		}else {
			members = memberRepository.findAllByEnabledOrderByJoindateDesc(pageable, false);
		}
		
		int startPage = 0;
		int endPage = 0;
		if(members!=null) {
			startPage = Math.max(1, members.getPageable().getPageNumber() - 4);
			endPage = Math.min(members.getTotalPages(), members.getPageable().getPageNumber() + 4);
		}
		
		model.addAttribute("members", members);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("searchType", searchType);
		
		return "administration/ibio/member/select/memberRegistrationCheck";
	}
	
	@GetMapping("/memberRegistrationCheckDetail")
	public String memberRegistrationCheckDetail() {
		
		return "administration/ibio/member/detail/memberRegistrationCheckDetail";
	}
	
	
	@GetMapping("/dealerManager")
	public String dealerManager() {
		
		return "administration/ibio/member/select/dealerManager";
	}
	
	@GetMapping("/dealerTradingManager")
	public String dealerTradingManager() {
		
		return "administration/ibio/member/select/dealerTradingManager";
	}
	
	@GetMapping("/dealerManagerDetail")
	public String dealerManagerDetail() {
		
		return "administration/ibio/member/detail/dealerManagerDetail";
	}
	
	@GetMapping("/dealerPaymentManagerDetail")
	public String dealerPaymentManagerDetail() {
		
		return "administration/ibio/member/detail/dealerPaymentManagerDetail";
	}
	
	@GetMapping("/dealerTradingManagerDetail")
	public String dealerTradingManagerDetail() {
		
		return "administration/ibio/member/detail/dealerTradingManagerDetail";
	}
	
	@GetMapping("/memberManager")
	public String memberManager() {
		
		return "administration/ibio/member/select/memberManager";
	}
	
	@GetMapping("/memberManagerDetail")
	public String memberManagerDetail() {
		
		return "administration/ibio/member/detail/memberManagerDetail";
	}
	
	@GetMapping("/memberPaymentManagerDetail")
	public String memberPaymentManagerDetail() {
		
		return "administration/ibio/member/detail/memberPaymentManagerDetail";
	}
	
	
	@GetMapping("/billManager")
	public String billManager() {
		
		return "administration/ibio/basic/select/billManager";
	}
	
	@GetMapping("/memberBillManagerDetail")
	public String memberBillManagerDetail() {
		
		return "administration/ibio/member/detail/memberBillManagerDetail";
	}
	
	@GetMapping("/memberBillManagerInsert")
	public String memberBillManagerInsert() {
		
		return "administration/ibio/member/insert/memberBillManagerInsert";
	}
	
	@GetMapping("/memberInquiryManager")
	public String memberInquiryManager() {
		
		return "administration/ibio/member/select/memberInquiryManager";
	}
	
	@GetMapping("/memberInquiryManagerDetail")
	public String memberInquiryManagerDetail() {
		
		return "administration/ibio/member/detail/memberInquiryManagerDetail";
	}
	
	@GetMapping("/dealerInquiryManager")
	public String dealerInquiryManager() {
		
		return "administration/ibio/member/select/dealerInquiryManager";
	}
	
	@GetMapping("/dealerInquiryManagerDetail")
	public String dealerInquiryManagerDetail() {
		
		return "administration/ibio/member/detail/dealerInquiryManagerDetail";
	}
	
	@GetMapping("/refundManager")
	public String refundManager() {
		
		return "administration/ibio/member/select/refundManager";
	}
	
	@GetMapping("/refundManagerDetail")
	public String refundManagerDetail() {
		
		return "administration/ibio/member/detail/refundManagerDetail";
	}
	
	@GetMapping("/couponManager")
	public String couponManager() {
		
		return "administration/ibio/member/select/couponManager";
	}
	
	@GetMapping("/couponManagerDetail")
	public String couponManagerDetail() {
		
		return "administration/ibio/member/detail/couponManagerDetail";
	}
	
	@GetMapping("/siteManager")
	public String siteManager() {
		
		return "administration/ibio/site/select/siteManager";
	}
	
	@GetMapping("/eventManager")
	public String eventManager() {
		
		return "administration/ibio/site/select/eventManager";
	}
	
	@GetMapping("/eventManagerInsert")
	public String eventManagerInsert() {
		
		return "administration/ibio/site/insert/eventManagerInsert";
	}
	
	@GetMapping("/eventManagerDetail")
	public String eventManagerDetail() {
		
		return "administration/ibio/site/detail/eventManagerDetail";
	}
	
	@GetMapping("/noticeManager")
	public String noticeManager() {
		
		return "administration/ibio/site/select/noticeManager";
	}
	
	@GetMapping("/noticeManagerInsert")
	public String noticeManagerInsert() {
		
		return "administration/ibio/site/insert/noticeManagerInsert";
	}
	
	@GetMapping("/noticeManagerDetail")
	public String noticeManagerDetail() {
		
		return "administration/ibio/site/detail/noticeManagerDetail";
	}
	
	@GetMapping("/faqManager")
	public String faqManager() {
		
		return "administration/ibio/site/select/faqManager";
	}
	
	@GetMapping("/faqManagerInsert")
	public String faqManagerInsert() {
		
		return "administration/ibio/site/select/faqManagerInsert";
	}
	
	@GetMapping("/faqManagerDetail")
	public String faqManagerDetail() {
		
		return "administration/ibio/site/detail/faqManagerDetail";
	}
	
	@GetMapping("/bannerManager")
	public String bannerManager() {
		
		return "administration/ibio/site/select/bannerManager";
	}
	
	@GetMapping("/accessManager")
	public String accessManager() {
		
		return "administration/ibio/advanced/select/accessManager";
	}
	
	@GetMapping("/accessManagerDetail")
	public String accessManagerDetail() {
		
		return "administration/ibio/advanced/detail/accessManagerDetail";
	}
	
	@GetMapping("/logManager")
	public String logManager() {
		
		return "administration/ibio/advanced/select/logManager";
	}
	
	@GetMapping("/logManagerDetail")
	public String logManagerDetail() {
		
		return "administration/ibio/advanced/detail/logManagerDetail";
	}
	
	@GetMapping("/fileManager")
	public String fileManager() {
		
		return "administration/ibio/advanced/select/fileManager";
	}
	
	@GetMapping("/fileManagerDetail")
	public String fileManagerDetail() {
		
		return "administration/ibio/advanced/select/fileManagerDetail";
	}
	
	@GetMapping("/mamberCalculationManager")
	public String mamberCalculationManager() {
		
		return "administration/ibio/basic/select/memberCalculationManager";
	}
	
	@GetMapping("/mamberCalculationManagerDetail")
	public String mamberCalculationManagerDetail() {
		
		return "administration/ibio/basic/detail/memberCalculationManagerDetail";
	}
	
	@GetMapping("/dealerCalculationManagerList")
	public String dealerCalculationManagerList() {
		
		return "administration/ibio/basic/select/dealerCalculationManagerList";
	}
	
	@GetMapping("/dealerCalculationManager")
	public String dealerCalculationManager() {
		
		return "administration/ibio/basic/select/dealerCalculationManager";
	}
	
	@GetMapping("/dealerCalculationManagerDetail")
	public String dealerCalculationManagerDetail() {
		
		return "administration/ibio/basic/detail/dealerCalculationManagerDetail";
	}
	

	@GetMapping("/ibioProductManager")
	public String ibioProductManager() {
		
		return "administration/ibio/product/select/ibioProductManager";
	}
	
	@GetMapping("/ibioProductManagerInsert")
	public String ibioProductManagerInsert() {
		
		return "administration/ibio/product/insert/ibioProductManagerInsert";
	}
	
	@GetMapping("/ibioProductManagerDetail")
	public String ibioProductManagerDetail() {
		
		return "administration/ibio/product/detail/ibioProductManagerDetail";
	}
	
	@GetMapping("/ibioProductCategoryManager")
	public String ibioProductCategoryManager() {
		
		return "administration/ibio/product/select/ibioProductCategoryManager";
	}
	
	@GetMapping("/ibioProductIndexManager")
	public String ibioProductIndexManager() {
		
		return "administration/ibio/product/select/ibioProductIndexManager";
	}
	
	@GetMapping("/dealerProductManager")
	public String dealerProductManager() {
		
		return "administration/ibio/product/select/dealerProductManager";
	}
	
	@GetMapping("/dealerProductManagerDetail")
	public String dealerProductManagerDetail() {
		
		return "administration/ibio/product/detail/dealerProductManagerDetail";
	}
	
	@GetMapping("/ibioProductInfoDumpManager")
	public String ibioProductInfoDumpManager() {
		
		return "administration/ibio/product/select/ibioProductInfoDumpManager";
	}
	
	@GetMapping("/ibioProductFileDumpManager")
	public String ibioProductFileDumpManager() {
		
		return "administration/ibio/product/select/ibioProductFileDumpManager";
	}
	
}
