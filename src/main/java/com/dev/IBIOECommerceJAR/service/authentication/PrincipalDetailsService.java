package com.dev.IBIOECommerceJAR.service.authentication;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dev.IBIOECommerceJAR.model.authentication.PrincipalDetails;
import com.dev.IBIOECommerceJAR.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;

	// 시큐리티 session -> Authentication -> UserDetails
	// 시큐리티 세션(내부 Authentication(내부 UserDetails(PrincipalDetails)))
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException,InternalAuthenticationServiceException  {
		
		log.info("PrincipalDetailService.loadUserByUsername");
		
		if(!memberRepository.findByUsername(username).isPresent()) {
			throw new UsernameNotFoundException(username);
		}
		
		
		return new PrincipalDetails(memberRepository.findByUsername(username).get());
	}
	
}
