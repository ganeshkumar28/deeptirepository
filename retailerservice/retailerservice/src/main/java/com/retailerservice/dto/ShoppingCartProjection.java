package com.retailerservice.dto;


public interface ShoppingCartProjection {
	
	long getCartId();
	long getQuantity();
	double getTotalPrice();
	String getProductDescription();
	String getProductName();

}
