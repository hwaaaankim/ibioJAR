package com.dev.IBIOECommerceJAR.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.IBIOECommerceJAR.model.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	Page<Product> findAllByOrderByProductClicks(Pageable pageable);
}
