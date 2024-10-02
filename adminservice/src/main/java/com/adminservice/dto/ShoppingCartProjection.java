package com.adminservice.dto;

public interface ShoppingCartProjection {
	
	long getCartId();
	long getQuantity();
	double getTotalPrice();
	String getProductDescription();
	String getProductName();
	double getprice();

}