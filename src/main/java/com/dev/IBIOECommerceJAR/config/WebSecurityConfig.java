package com.dev.IBIOECommerceJAR.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

import com.dev.IBIOECommerceJAR.handler.CustomAccessDeniedHandler;
import com.dev.IBIOECommerceJAR.handler.CustomAuthFailureHandler;
import com.dev.IBIOECommerceJAR.handler.ExceptionAuthenticationEntryPoint;
import com.dev.IBIOECommerceJAR.service.authentication.PrincipalDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {

	// 일반 웹 페이지 조회 URL
	// 회원가입, 로그인 페이지
	private final String[] visitorsUrls = {
			"/**", 
			"/front/**", 
			"/administration/**",
			"/api/v1/**", 
			"/logout", 
			"/signUp",
			"/registration"
	};
	
	// 제품 구매관련
	private final String[] membersUrls = {
			"/member/**"
	};
	
	// 딜러 제품 판매 관리
	private final String[] dealersUrls = {
			"/dealer/**"
	};
	
	// 쇼핑몰 관리자
	private final String[] adminsUrls = {
			"/admin/**"
	};
	
	// private final AuthenticationProvider authenticationProvider;
	private final PrincipalDetailsService principalDetailsService;
	private final CustomAuthFailureHandler customFailureHandler;
	private final ExceptionAuthenticationEntryPoint exceptionAuthenticationEntryPoint;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;
//	private final PasswordEncoder passwordEncoder;
	
	
	@Bean
	HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}
	
	@Bean
	SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
	
	
//	@Bean
//	DaoAuthenticationProvider daoAuthenticationProvider() {
//		log.info("DaoAuthenticationProvider");
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(principalDetailsService);
//        authenticationProvider.setPasswordEncoder(passwordEncoder);
//        authenticationProvider.setHideUserNotFoundExceptions(false);
//        return authenticationProvider;
//    }
//	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
//		AuthenticationManagerBuilder sharedObject = http.getSharedObject(AuthenticationManagerBuilder.class);
//		sharedObject.authenticationProvider(daoAuthenticationProvider());
//      AuthenticationManager authenticationManager = sharedObject.build();

    
		http.csrf((csrfConfig) -> 
				csrfConfig
					.disable()) // 1번
			.headers((headerConfig) -> 
				headerConfig
					.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()))
			.authorizeHttpRequests((authorizeRequests) -> 
				authorizeRequests
					.requestMatchers(adminsUrls).hasAuthority("ROLE_ADMIN")
					.requestMatchers(dealersUrls).hasAnyAuthority("ROLE_DEALER")
					.requestMatchers(membersUrls).hasAnyAuthority("ROLE_MEMBER", "ROLE_DEALER")
					.requestMatchers(visitorsUrls).permitAll()
					.anyRequest().authenticated())
			.sessionManagement(httpSecuritySessionManagermentConfigurer ->
				httpSecuritySessionManagermentConfigurer
					.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
			.formLogin((formLogin) -> 
				formLogin
					.loginPage("/loginForm")
					.usernameParameter("username")
					.passwordParameter("password")
					.loginProcessingUrl("/signinProcess")
					.defaultSuccessUrl("/", false)
					.failureHandler(customFailureHandler))
//			.exceptionHandling((exception) -> {
//				exception
//					.accessDeniedPage("/error/403");
//			})
//		    .authenticationProvider(daoAuthenticationProvider())
			.exceptionHandling(e->{
				e.accessDeniedHandler(customAccessDeniedHandler)
				 .authenticationEntryPoint(exceptionAuthenticationEntryPoint);
			})
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
