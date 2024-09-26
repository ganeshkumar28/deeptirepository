package com.retailerservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retailerservice.dto.FavoritesProjection;
import com.retailerservice.dto.ShoppingCartProjection;
import com.retailerservice.entity.Favorites;
import com.retailerservice.entity.Products;
import com.retailerservice.entity.ShoppingCart;
import com.retailerservice.entity.User;
import com.retailerservice.service.BuyerServiceInterface;

import jakarta.ws.rs.Path;

@RestController
@RequestMapping("/buyer")
public class BuyerControllerr {

	@Autowired
	private BuyerServiceInterface bservice;

	@PostMapping("/addProductToCart/{userId}")
	public ResponseEntity<ShoppingCart> addProductToCart(@RequestBody ShoppingCart shoppingcart,@PathVariable("userId") Long userId) {
		ShoppingCart cartItems = bservice.addProductToCart(shoppingcart,userId);
		if (cartItems != null) {
			return new ResponseEntity<>(cartItems, HttpStatus.OK);
		} 
		return null;
	}

	@PostMapping("/addProductToFavorite/{userId}")
	public ResponseEntity<String> addProductToFavorite(@RequestBody Favorites favorites,@PathVariable("userId") Long userId) {
		Optional<Favorites> favorite = bservice.addProductToFavorite(favorites,userId);
		String message = "Item added successfully";
		if (favorite != null) {
			return new ResponseEntity<>(message, HttpStatus.OK);
		} else {
			message = "Failed to add item";
			return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
		}

	}

	@GetMapping("/viewCartProducts/{userId}")
	public ResponseEntity<List<ShoppingCartProjection>> viewCartProducts(@PathVariable("userId") Long userId) {
		List<ShoppingCartProjection> cartItems = bservice.viewCartProducts(userId);

		if (cartItems != null && !cartItems.isEmpty()) {
			return new ResponseEntity<>(cartItems, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(cartItems,HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/viewFavoriteProducts/{userId}")
	public ResponseEntity<Object> viewFavoritePrducts(@PathVariable("userId") Long userId) {
		Optional<FavoritesProjection> favItems = bservice.viewFavoriteProducts(userId);
		if (favItems != null) {
			return new ResponseEntity<>(favItems, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(favItems, HttpStatus.NO_CONTENT);

		}

	}
	
	@DeleteMapping("/deleteProductFromCart/{userId}/{productId}")
	public ResponseEntity<Object> deleteProductFromCart(@PathVariable("userId") Long userId, @PathVariable("productId") Long productId)
	{
		Boolean deleted = bservice.deleteProductFromCart(userId,productId);
		List<ShoppingCartProjection> cartItems = bservice.viewCartProducts(userId);
		if(deleted == true)
		{
			return new ResponseEntity<>(cartItems, HttpStatus.OK);
		}
		else 
		{
			String message = "Cart is empty";
			return new ResponseEntity<>(message,HttpStatus.NO_CONTENT);
		}
		
	}
	
	@DeleteMapping("/deleteProductFromFavorite/{userId}/{productId}")
	public ResponseEntity<Object> deleteProductFromFavorite(@PathVariable("userId") Long userId, @PathVariable("productId") Long productId)
	{
		Boolean deleted = bservice.deleteProductFromFavorite(userId,productId);
		String message = "Failed to delete";
		if(deleted == true)
		{
			message = "Deleted Successfully";
			return new ResponseEntity<>(message,HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(message,HttpStatus.NO_CONTENT);
		}
		
	}
	
	@PutMapping("/updateQuantity/{userId}/{productId}/{quantity}")
    public ResponseEntity<List<ShoppingCartProjection>> updateCartQuantity(@PathVariable Long userId, @PathVariable Long productId, @PathVariable Long quantity) {
        List<ShoppingCartProjection> updatedCartItem = bservice.updateCartQuantity(userId, productId, quantity);
        return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);
    }
	
	@GetMapping("getuser/{uid}")
	public User getUser(@PathVariable("uid") long uid) {
		return bservice.getUser(uid);
	}
	
	@GetMapping("getProduct/{pid}")
	public Products getProduct(@PathVariable("pid") long pid) {
		return bservice.getProduct(pid);
	}
	

}
