package com.sellerservice.controller;

import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sellerservice.entity.Products;
import com.sellerservice.entity.Review;
import com.sellerservice.entity.User;
import com.sellerservice.dto.ProductsProjection;
import com.sellerservice.entity.Orders;
import com.sellerservice.service.SellerServiceInterface;

@RestController
@RequestMapping("product")
public class SellerServiceClass {
    
    @Autowired
    private SellerServiceInterface sellerServiceInterface;

    @PostMapping("addProduct/{userId}")
	public ResponseEntity<Object> addProduct(@PathVariable("userId") Long userId,@RequestBody Products product)
	{
    	
    	System.out.println("fvgbhjk"+userId);
    	
    	product.setUserId(userId);
		Optional<Products> products = sellerServiceInterface.addProduct(product);
		Products p1=products.get();
		if(p1!=null)
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
		List<ProductsProjection> product = sellerServiceInterface.viewProducts(userId);
		System.out.println(product);
		if(product != null)
		{
			return new ResponseEntity<>(product, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(product, HttpStatus.NO_CONTENT);

		}
		
	}
    
    @GetMapping("/getProduct")
    public ResponseEntity<List<ProductsProjection>> getProducts()
	{
		List<ProductsProjection> product = sellerServiceInterface.getProducts();
		System.out.println(product);
		if(product != null)
		{
			return new ResponseEntity<>(product, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(product, HttpStatus.NO_CONTENT);

		}
		
	}

    @PostMapping("/edit/{productId}")
    public ResponseEntity<?> editProduct(@PathVariable("productId") long productId,@RequestBody Products product) {
    	
    	product.setProductId(productId);
        boolean productEdited = sellerServiceInterface.editProduct(product);
        System.out.println(productEdited);
        if (productEdited) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong.");
        }
    }

    
}
