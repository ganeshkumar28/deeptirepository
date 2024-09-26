package com.retailerservice.service;

import java.util.List;
import java.util.Optional;


import com.retailerservice.dto.FavoritesProjection;
import com.retailerservice.dto.ShoppingCartProjection;
import com.retailerservice.entity.Favorites;
import com.retailerservice.entity.Products;
import com.retailerservice.entity.ShoppingCart;
import com.retailerservice.entity.User;

public interface BuyerServiceInterface {

	ShoppingCart addProductToCart(ShoppingCart shoppingcart, Long userId);

	Optional<Favorites> addProductToFavorite(Favorites favorites,Long userId);

	List<ShoppingCartProjection> viewCartProducts(long userId);

	Optional<FavoritesProjection> viewFavoriteProducts(Long userId);

	Boolean deleteProductFromCart(Long userId, Long productId);

	Boolean deleteProductFromFavorite(Long userId, Long productId);

	List<ShoppingCartProjection> updateCartQuantity(Long userId, Long productId, Long newQuantity);

	User getUser(long uid);

	Products getProduct(long pid);

}
