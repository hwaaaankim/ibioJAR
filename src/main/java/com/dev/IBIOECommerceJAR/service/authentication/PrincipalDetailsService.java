package com.dev.IBIOECommerceJAR.service.authentication;

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

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException,InternalAuthenticationServiceException  {
//		
//		log.info("PrincipalDetailService.loadUserByUsername");
//		
//		if(!memberRepository.findByUsername(username).isPresent()) {
//			throw new UsernameNotFoundException(username);
//		}
//		
//		
//		return new PrincipalDetails(memberRepository.findByUsername(username).get());
//	}
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("PrincipalDetailsService.loadUserByUsername");

        return memberRepository.findByUsername(username)
            .map(PrincipalDetails::new)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
	
}
