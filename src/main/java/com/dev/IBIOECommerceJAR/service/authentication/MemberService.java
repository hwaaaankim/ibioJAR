package com.dev.IBIOECommerceJAR.service.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dev.IBIOECommerceJAR.model.authentication.Member;
import com.dev.IBIOECommerceJAR.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Service
@Slf4j
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;
	
	@Bean(name = "saveBean")
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public Member insertAdmin(Member member) {
		log.info("NEW ADMIN");
		String encodedPassword = passwordEncoder().encode(member.getPassword());
		member.setPassword(encodedPassword);
		member.setRole("ROLE_" + member.getRole());
		
		return memberRepository.save(member);

	}
}
