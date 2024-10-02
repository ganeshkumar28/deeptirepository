package com.buyerservice.dto;


public interface ReviewProjection {
	long getReviewId();
	String getReviewText();
	int getRating();
	Long getProductId();      // Navigate through 'product' relationship to get productId
    String getProductName();  // Navigate through 'product' relationship to get productName
	
}
