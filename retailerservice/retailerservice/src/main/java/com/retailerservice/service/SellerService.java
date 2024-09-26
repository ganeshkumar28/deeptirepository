package com.retailerservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.retailerservice.dao.ProductRepository;
import com.retailerservice.dto.ProductsProjection;
import com.retailerservice.dto.ShoppingCartProjection;
import com.retailerservice.entity.Products;
import com.retailerservice.entity.User;

@Service
@Transactional
public class SellerService implements SellerServiceInterface {

	@Autowired
	private ProductRepository prorep;

	@Override
	public Optional<Products> addProduct(Products product,Long userId) {
		// TODO Auto-generated method stub
		prorep.findById(userId);
		Products savedProduct = prorep.save(product);
		return Optional.of(savedProduct);

//		User user = product.getUser();
//		if (user != null) {
//			Optional<User> user1 = prorep.findUserById(user.getUserId());
//			if (user1.isPresent()) {
//				product.setUser(user1.get());
//			} else {
//				return Optional.of(product);
//			}
		}

	@Override
	public List<ProductsProjection> viewProducts(Long userId) {
		// TODO Auto-generated method stub
		List<ProductsProjection> product = prorep.findProductByUserId(userId);
		return product;
	}

		
	

}
