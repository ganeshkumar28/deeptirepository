package com.retailerservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retailerservice.dto.ProductsProjection;
import com.retailerservice.dto.ShoppingCartProjection;
import com.retailerservice.entity.Products;
import com.retailerservice.service.SellerServiceInterface;

@RestController
@RequestMapping("/seller")
public class SellerController {
	
	@Autowired
	private SellerServiceInterface sservice; 
	
	@PostMapping("/addProduct/{userId}")
	public ResponseEntity<Object> addProduct(@RequestBody Products product,@PathVariable("userId") Long userId)
	{
		Optional<Products> products = sservice.addProduct(product,userId);
		if(products.isPresent())
		{
			return new ResponseEntity<>(products, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(products,HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/viewProduct/{userId}")
	public ResponseEntity<List<ProductsProjection>> viewProducts(@PathVariable("userId") Long userId)
	{
		List<ProductsProjection> product = sservice.viewProducts(userId);
		if(product != null)
		{
			return new ResponseEntity<>(product, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(product, HttpStatus.NO_CONTENT);

		}
		
	}

}
