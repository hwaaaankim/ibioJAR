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

import com.dev.IBIOECommerceJAR.handler.CustomAuthFailureHandler;
import com.dev.IBIOECommerceJAR.service.authentication.PrincipalDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private final String[] visitorsUrls = {
            "/**", 
            "/front/**", 
            "/administration/**",
            "/api/v1/**", 
            "/logout", 
            "/signUp",
            "/registration",
            "/checkLogin", // 공통 로그인 체크 엔드포인트
            "/error"
            
    };
    
    private final String[] membersUrls = {
            "/member/**"
    };
    
    private final String[] dealersUrls = {
            "/dealer/**"
    };
    
    private final String[] adminsUrls = {
            "/admin/**"
    };
    
    private final String[] shoppingUrls = {
            "/shopping/**" // 쇼핑 관련 엔드포인트
    };
    
    private final PrincipalDetailsService principalDetailsService;
    private final CustomAuthFailureHandler customFailureHandler;
    
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
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(options -> options.sameOrigin()))
            .authorizeHttpRequests(authorizeRequests -> 
                authorizeRequests
                    .requestMatchers(adminsUrls).hasAuthority("ROLE_ADMIN")
                    .requestMatchers(dealersUrls).hasAnyAuthority("ROLE_DEALER")
                    .requestMatchers(membersUrls).hasAnyAuthority("ROLE_MEMBER", "ROLE_DEALER")
                    .requestMatchers(shoppingUrls).hasAnyAuthority("ROLE_MEMBER", "ROLE_DEALER", "ROLE_ADMIN")
                    .requestMatchers(visitorsUrls).permitAll()
                    .anyRequest().authenticated())
            .sessionManagement(sessionManagement -> 
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
            .formLogin(formLogin -> 
                formLogin
                    .loginPage("/loginForm")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginProcessingUrl("/signinProcess")
                    .defaultSuccessUrl("/", false)
                    .failureHandler(customFailureHandler))
            .rememberMe(rememberMe -> 
                rememberMe
                    .rememberMeParameter("remember")
                    .userDetailsService(principalDetailsService)
                    .tokenValiditySeconds(60 * 60 * 24 * 30)
                    .alwaysRemember(false))
            .logout(logout -> 
                logout
                    .logoutUrl("/logout")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .logoutSuccessUrl("/index"));
        return http.build();
    }
}

