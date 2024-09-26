package com.retailerservice.service;

import java.util.List;
import java.util.Optional;

import com.retailerservice.dto.ProductsProjection;
import com.retailerservice.entity.Products;

public interface SellerServiceInterface {

	Optional<Products> addProduct(Products product, Long userId);

	List<ProductsProjection> viewProducts(Long userId);

}
