package com.buyerservice.controller;

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

import com.buyerservice.dto.FavoritesProjection;
import com.buyerservice.dto.ReviewProjection;
import com.buyerservice.dto.ShoppingCartProjection;
import com.buyerservice.dto.UserProjection;
import com.buyerservice.entity.Favorites;
import com.buyerservice.entity.Orders;
import com.buyerservice.entity.Products;
import com.buyerservice.entity.Review;
import com.buyerservice.entity.ShoppingCart;
import com.buyerservice.entity.User;
import com.buyerservice.service.BuyerServiceInterface;

import jakarta.ws.rs.Path;

@RestController
@RequestMapping("/buyer")
public class CustomerController {

	@Autowired
	private BuyerServiceInterface bservice;

	
	@GetMapping("/login/{email}/{password}")
	public ResponseEntity<User> login(@PathVariable("email") String email, @PathVariable("password") String password) {
		
		System.out.println("rest" +email);
		System.out.println("rest" +password);
		
		UserProjection userProjection = bservice.login(email, password);
	    
	    if (userProjection != null) {
	        // Manually map the projection to a User entity if needed
	        User user = new User();
	        user.setUserId(userProjection.getUserId());  // Assuming UserProjection has getUserId()
	        user.setEmail(userProjection.getEmail());    // Assuming UserProjection has getEmail()
	        user.setName(userProjection.getName());      // Assuming UserProjection has getName()
	        user.setUserType(userProjection.getUserType());
	        user.setStatus(userProjection.getStatus());
	        // Map other fields as necessary
	        
	        return new ResponseEntity<>(user, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	    }
	}
	
	
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

	@GetMapping("/cart/{userId}")
	public ResponseEntity<List<ShoppingCartProjection>> viewCartProducts(@PathVariable("userId") Long userId) {
		System.out.println(userId);
		List<ShoppingCartProjection> cartItems = bservice.viewCartProducts(userId);
		System.out.println(cartItems);
		

		if (cartItems != null && !cartItems.isEmpty()) {
			return new ResponseEntity<>(cartItems, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(cartItems,HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/wishlist/{userId}")
	public ResponseEntity<List<FavoritesProjection>> viewFavoritePrducts(@PathVariable("userId") Long userId) {
		System.out.println(userId);
		List<FavoritesProjection> favItems = bservice.viewFavoriteProducts(userId);
		System.out.println(favItems);
		if (favItems != null) {
			return new ResponseEntity<>(favItems, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(favItems, HttpStatus.NO_CONTENT);

		}

	}
	
	@DeleteMapping("/deleteProductFromCart/{userId}/{cartId}")
	public ResponseEntity<Object> deleteProductFromCart(@PathVariable("userId") Long userId, @PathVariable("cartId") Long cartId)
	{
		System.out.println(userId);
		System.out.println(cartId);
		Boolean deleted = bservice.deleteProductFromCart(cartId);
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
	
	@DeleteMapping("/deleteProductFromFavorite/{userId}/{favoriteId}")
	public ResponseEntity<Object> deleteProductFromFavorite(@PathVariable("userId") Long userId, @PathVariable("favoriteId") Long favoriteId)
	{
		System.out.println(userId);
		System.out.println(favoriteId);
		
//		Boolean deleted = bservice.deleteProductFromFavorite(userId,favoriteId);
		Boolean deleted = bservice.deleteProductFromFavorite(favoriteId);
		
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
	
	@PutMapping("/updateQuantity/{userId}/{cartId}/{quantity}")
    public ResponseEntity<List<ShoppingCartProjection>> updateCartQuantity(@PathVariable("userId") Long userId , @PathVariable("cartId") Long cartId, @PathVariable("quantity") Long quantity) {
        System.out.println(userId);
        System.out.println(cartId);
        System.out.println(quantity);
		List<ShoppingCartProjection> updatedCartItem = bservice.updateCartQuantity(userId, cartId, quantity);
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
	
	
	
	
	
	
	
	// Bhanu Section
	@PostMapping("/submitreview")
	public ResponseEntity<String> submitreview(@RequestBody Review review) {
		System.out.println("dfghj");
//		System.out.println("Received review: " + userId);
//		User us = new User();
//		us.setUserId(userId);
//		System.out.println(us.getUserId());
//		Review rv = new Review();
//		rv.setUser(us);
//		System.out.println(rv.getUser().getUserId());
		
			int i=bservice.submitreview(review);
			String message="failed";
			if(i>0) {
				message="success";
			}
			ResponseEntity<String> rentity=new ResponseEntity<String>(message ,HttpStatus.OK);
			return rentity;

	}
	
	
	@GetMapping("/myreviews/{userId}")
	public ResponseEntity<List<ReviewProjection>> viewreviews(@PathVariable("userId") Long userId){
		System.out.println(userId);
		List<ReviewProjection> reviews = bservice.getAllreviews(userId);
		System.out.println(reviews);
		if (reviews != null) {
			return new ResponseEntity<>(reviews, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(reviews, HttpStatus.NO_CONTENT);

		}
	}
	
	
	 @DeleteMapping("/reviews/{prid}")
	 public ResponseEntity<Object> deleteReview(@PathVariable("prid") Long prid) {
		 System.out.println(prid);
		 Boolean deleted = bservice.deleteReviewById(prid);	            
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
	
	 @PostMapping("/submitorder")
		public ResponseEntity<Object> submitorder(@RequestBody Orders order) {
			
			
			int i=bservice.submitorder(order);
			String message="success";
			if(i>0) {
				message="success";
			}
			ResponseEntity<Object> rentity=new ResponseEntity<Object>(message ,HttpStatus.OK);
			return rentity;
	 }
	
	 
	 @GetMapping("/viewproduct/{pid}")
	    public ResponseEntity<Object> viewproduct(@PathVariable("pid") Long pid) {
	        Products p = bservice.getproductByUserId(pid);
	        System.out.println(p);
	        ResponseEntity<Object> rentity=new ResponseEntity<Object>(p ,HttpStatus.OK);
			return rentity;
	 }
	 
	 @GetMapping("/viewuser/{uid}")
	 public ResponseEntity<Object> viewuser(@PathVariable("uid") Long uid) {
	        User u =bservice.getByUserId(uid);
	        System.out.println(u);
	        ResponseEntity<Object> rentity=new ResponseEntity<Object>(u ,HttpStatus.OK);
	        
	        return rentity;
	 }
	

}