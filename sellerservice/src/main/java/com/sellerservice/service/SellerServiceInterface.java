package com.sellerservice.service;

import java.util.List;
import java.util.Optional;

import com.sellerservice.dto.ProductsProjection;
import com.sellerservice.entity.Products;

public interface SellerServiceInterface {

	Optional<Products> addProduct(Products product);

	List<ProductsProjection> viewProducts(Long userId);

	boolean editProduct(Products product);

	List<ProductsProjection> getProducts();

}