package com.buyerservice.service;

import java.util.List;
import java.util.Optional;

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

public interface BuyerServiceInterface {

	ShoppingCart addProductToCart(ShoppingCart shoppingcart, Long userId);

	Optional<Favorites> addProductToFavorite(Favorites favorites, Long userId);

	List<ShoppingCartProjection> viewCartProducts(Long userId);

	List<FavoritesProjection> viewFavoriteProducts(Long userId);

	Boolean deleteProductFromCart(Long productId);

	List<ShoppingCartProjection> updateCartQuantity(Long userId, Long productId, Long quantity);

	User getUser(long uid);

	Products getProduct(long pid);

	Boolean deleteProductFromFavorite(Long favoriteId);

	
	// Bhanu section
	int submitreview(Review review);

	int submitorder(Orders order);

	Boolean deleteReviewById(Long prid);

	List<ReviewProjection> getAllreviews(Long userId);

	Products getproductByUserId(Long pid);

	User getByUserId(Long uid);

	
	//login
	UserProjection login(String email, String password);

}
