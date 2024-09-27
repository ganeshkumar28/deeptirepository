package com.buyerservice.service;

import java.util.List;
import java.util.Optional;

import com.buyerservice.dto.FavoritesProjection;
import com.buyerservice.dto.ShoppingCartProjection;
import com.buyerservice.entity.Favorites;
import com.buyerservice.entity.Products;
import com.buyerservice.entity.ShoppingCart;
import com.buyerservice.entity.User;

public interface BuyerServiceInterface {

	ShoppingCart addProductToCart(ShoppingCart shoppingcart, Long userId);

	Optional<Favorites> addProductToFavorite(Favorites favorites, Long userId);

	List<ShoppingCartProjection> viewCartProducts(Long userId);

	List<FavoritesProjection> viewFavoriteProducts(Long userId);

	Boolean deleteProductFromCart(Long productId);

	Boolean deleteProductFromFavorite(Long userId, Long productId);

	List<ShoppingCartProjection> updateCartQuantity(Long userId, Long productId, Long quantity);

	User getUser(long uid);

	Products getProduct(long pid);

}
