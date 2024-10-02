package com.sellerservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sellerservice.dao.SellerDaoInterface;
import com.sellerservice.dto.ProductsProjection;
import com.sellerservice.entity.Products;
import com.sellerservice.entity.User;

@Service
@Transactional
public class SellerService implements SellerServiceInterface {
	
	@Autowired
	private SellerDaoInterface sellerDao;

	@Override
	public Optional<Products> addProduct(Products product) {
		// TODO Auto-generated method stub
		User user = product.getUser();
		if (user != null) {
			Optional<User> user1 = sellerDao.findUserById(user.getUserId());
			if (user1.isPresent()) {
				product.setUser(user1.get());
			} else {
				return Optional.of(product);
			}
		}

		Products savedProduct = sellerDao.save(product);
		return Optional.of(savedProduct);
	}

	@Override
	public List<ProductsProjection> viewProducts(Long userId) {
		// TODO Auto-generated method stub
		List<ProductsProjection> product = sellerDao.findProductByUserId(userId);
		return product;
	}

	 @Override
	    public boolean editProduct(Products product) {
	        try {
	            // Fetch the existing product by ID
	            Optional<Products> existingProduct1 = sellerDao.findById(product.getProductId());
	            
	            Products existingProduct=existingProduct1.get();
	            System.out.println("cvbn "+existingProduct.getQuantity());
	            
	            if (existingProduct != null) {
	                // Update the existing product's fields
	                existingProduct.setProductName(product.getProductName());
	                existingProduct.setProductDescription(product.getProductDescription());
	                existingProduct.setImageUrl(product.getImageUrl());
	                existingProduct.setProductCategory(product.getProductCategory());
	                existingProduct.setPrice(product.getPrice());
	                existingProduct.setDiscountPrice(product.getDiscountPrice());
	                existingProduct.setQuantity(product.getQuantity());
	                
	                // Save the updated product back to the database
	                sellerDao.save(existingProduct);
	                return true;
	            }
	            return false; // Product not found
	        } catch (Exception e) {
	            // Log the exception
	            e.printStackTrace();
	            return false;
	        }
	    }

	@Override
	public List<ProductsProjection> getProducts() {
		List<ProductsProjection> product = sellerDao.findProducts();
		return product;
	}

}
