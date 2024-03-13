package com.dev.IBIOECommerceJAR.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

import com.dev.IBIOECommerceJAR.service.authentication.PrincipalDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {

	private final String[] visitorsUrls = {
			"/**", "/front/**", "/administration/**" ,"/api/v1/**" , "/logout"
	};
	
	private final String[] membersUrls = {
			"/member/**",
	};
	
	private final String[] dealersUrls = {
			"/dealer/**"
	};

	private final String[] adminsUrls = {
			"/admin/**"
	};
	
	private final AuthenticationProvider authenticationProvider;
	private final PrincipalDetailsService principalDetailsService;
	
	@Bean
	SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.csrf((csrfConfig) -> 
				csrfConfig
					.disable()) // 1번
			.headers((headerConfig) -> 
				headerConfig
					.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))
			.authorizeHttpRequests((authorizeRequests) -> 
				authorizeRequests
					.requestMatchers(adminsUrls).hasAuthority("ROLE_ADMIN")
					.requestMatchers(dealersUrls).hasAnyAuthority("ROLE_ADMIN", "ROLE_DEALER")
					.requestMatchers(membersUrls).hasAnyAuthority("ROLE_ADMIN", "ROLE_DEALER", "ROLE_MEMBER")
					.requestMatchers(visitorsUrls).permitAll()
					.anyRequest().authenticated())
			.authenticationProvider(authenticationProvider)
			.formLogin((formLogin) -> 
				formLogin
					.loginPage("/loginForm")
					.usernameParameter("username")
					.passwordParameter("password")
					.loginProcessingUrl("/signinProcess")
					.defaultSuccessUrl("/", false))
			.rememberMe((remember) -> 
				remember
					.rememberMeParameter("remember")
					.userDetailsService(principalDetailsService)
					.tokenValiditySeconds(60*60*24*30)
					.alwaysRemember(false))
			.logout((logoutConfig) -> 
				logoutConfig
					.logoutUrl("/logout")
					.deleteCookies("JSESSIONID")
					.invalidateHttpSession(true)
					.logoutSuccessUrl("/index"));
		return http.build();
	}

}
