package com.dev.IBIOECommerceJAR.model.authentication;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class PrincipalDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private final Member member;

	public PrincipalDetails(Member member) {
		this.member = member;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new SimpleGrantedAuthority(member.getRole()));
        log.info("getAuthoritied");
        log.info(member.getRole());
        log.info(auth.get(0).getAuthority());
        return auth;
	}
	
//	public String getMembergrade() {
//		
//		if(this.member.getMemberGrade() == 2 || this.member.getMemberGrade() == 3) {
//			return "MANAGER";
//		}else {
//			return "ADMINISTRATION";
//		}
//		
//	}
//	
//	public String getMembername() {
//		
//		return this.member.getMemberName();
//	}
	
    @Override
    public String getUsername() {
        return this.member.getUsername();
    }

    @Override
    public String getPassword() {
        return this.member.getPassword();
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public boolean isEnabled() {
		return true;
	}

//	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//	@Override
//	public boolean isEnabled() {
//		return this.member.isMemberEnabled();
//	}
}