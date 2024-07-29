package com.dev.IBIOECommerceJAR.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dev.IBIOECommerceJAR.model.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	Page<Product> findAllByOrderByProductClicks(Pageable pageable);
	
	Page<Product> findAllByOrderByIdDesc(Pageable pageable);
	
	@Query("SELECT p FROM Product p WHERE "
            + "(:bigId = 0L OR p.bigSort.id = :bigId) AND "
            + "(:middleId = 0L OR p.middleSort.id = :middleId) AND "
            + "(:smallId = 0L OR p.smallSort.id = :smallId) AND "
            + "(:minCost IS NULL OR p.productPrice >= :minCost) AND "
            + "(:maxCost IS NULL OR p.productPrice <= :maxCost) AND "
            + "(:productSort = 'all' OR (p.productEventSign = true AND :productSort = 'yes') OR (p.productEventSign = false AND :productSort = 'no')) AND "
            + "(:productDiscount = 'all' OR (p.productDiscountSign = true AND :productDiscount = 'yes') OR (p.productDiscountSign = false AND :productDiscount = 'no')) AND "
            + "(:sellingResult = 'all' OR (p.productSell > 0 AND :sellingResult = 'yes') OR (p.productSell = 0 AND :sellingResult = 'no')) AND "
            + "(:searchWord IS NULL OR p.productCode LIKE %:searchWord% OR p.productTitle LIKE %:searchWord%)")
    Page<Product> findByConditions(
            @Param("bigId") Long bigId,
            @Param("middleId") Long middleId,
            @Param("smallId") Long smallId,
            @Param("minCost") Integer minCost,
            @Param("maxCost") Integer maxCost,
            @Param("productSort") String productSort,
            @Param("productDiscount") String productDiscount,
            @Param("sellingResult") String sellingResult,
            @Param("searchWord") String searchWord,
            Pageable pageable);
}
