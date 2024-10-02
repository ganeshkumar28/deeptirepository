package com.buyerservice.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.buyerservice.dto.ReviewProjection;
import com.buyerservice.entity.Review;

public interface ReviewInterface extends JpaRepository<Review,Long> {

	
	 @Query(nativeQuery = true, value ="SELECT r.review_id , r.review_text , r.rating , \r\n"
	 		+ "               p.product_id , p.product_name \r\n"
	 		+ "               FROM review r \r\n"
	 		+ "               JOIN products p ON r.product_product_id = p.product_id\r\n"
	 		+ "               WHERE r.user_user_id =:userId")
	 List<ReviewProjection> findbyuserid(@Param("userId") Long userId);

//	 @Query(nativeQuery = true, value ="Delete from review sc WHERE sc.product_product_id = :prid")
//	 Optional<Review> deletebyproid(@Param("prid")Long prid);
	 
	 @Query(nativeQuery = true, value ="select * from review r where r.review_id = :prid ")
	Optional<Review> findReviewByProductId(Long prid);
}
