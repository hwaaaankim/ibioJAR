package com.dev.IBIOECommerceJAR.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.IBIOECommerceJAR.model.authentication.MemberFile;

public interface MemberFileRepository extends JpaRepository<MemberFile, Long>{

	
}
