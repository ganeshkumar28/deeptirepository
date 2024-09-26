package com.retailerservice.dto;

public interface ProductsProjection {
	
	long getProductId();
	String getProductName();
	public String getProductDescription();
	public String getImageUrl();
	public String getProductCategory();
	double getPrice();
	double getDiscountPrice();
	long getQuantity();	

}
