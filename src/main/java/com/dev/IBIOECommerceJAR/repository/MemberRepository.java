package com.dev.IBIOECommerceJAR.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.IBIOECommerceJAR.model.authentication.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
	
	Optional<Member> findByUsername(String username);
	
	Optional<Member> findByPhone(String phone);
	
	Optional<Member> findByEmail(String email);
	
	Page<Member> findAllByEnabledOrderByJoindateDesc(Pageable pageable, Boolean enabled);
	
	Page<Member> findAllByEnabledAndNameContainingOrderByJoindateDesc(Pageable pageable, Boolean enabled, String name);
	
	Page<Member> findAllByEnabledAndUsernameContainingOrderByJoindateDesc(Pageable pageable, Boolean enabled, String username);
	
	Page<Member> findAllByEnabledAndPhoneContainingOrderByJoindateDesc(Pageable pageable, Boolean enabled, String phone);
	
	Page<Member> findAllByEnabledAndEmailContainingOrderByJoindateDesc(Pageable pageable, Boolean enabled, String email);
	
	Page<Member> findAllByEnabledAndBusinessContainingOrderByJoindateDesc(Pageable pageable, Boolean enabled, String business);
	
	Page<Member> findAllByEnabledAndJoindateLessThan(Pageable pageable, Boolean enabled, Date date);
	
	Page<Member> findAllByEnabledAndJoindateGreaterThan(Pageable pageable, Boolean enabled, Date date);
	
	Page<Member> findAllByEnabledAndJoindateBetween(Pageable pageable, Boolean enabled, Date startDate, Date endDate);

}






