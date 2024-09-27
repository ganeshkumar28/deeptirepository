package com.buyerservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buyerservice.dao.FavoritesInterface;
import com.buyerservice.dao.ProductRepository;
import com.buyerservice.dao.ShoppingcartInterface;
import com.buyerservice.dao.UserInterface;
import com.buyerservice.dto.FavoritesProjection;
import com.buyerservice.dto.ShoppingCartProjection;
import com.buyerservice.entity.Favorites;
import com.buyerservice.entity.Products;
import com.buyerservice.entity.ShoppingCart;
import com.buyerservice.entity.User;

@Service
@Transactional
public class BuyerService implements BuyerServiceInterface {

	@Autowired
	private ShoppingcartInterface bshop;

	@Autowired
	private FavoritesInterface bfav;
	
	@Autowired
	private UserInterface buser;
	
	@Autowired
	private ProductRepository bproduct;

	@Override
	public ShoppingCart addProductToCart(ShoppingCart shoppingcart,Long userId) {

		Optional<ShoppingCart> existingCart = bshop.findCartByUserID(userId);
		User user=buser.getByUserId(userId);
		if (existingCart.isPresent()) {
	        ShoppingCart cartItems = existingCart.get();
	        
	        cartItems.setQuantity(cartItems.getQuantity() + shoppingcart.getQuantity());
	        cartItems.setTotalPrice(cartItems.getTotalPrice() + shoppingcart.getTotalPrice());

	        ShoppingCart updatedCart = bshop.save(cartItems);
	        
	        return updatedCart;

	    } else {
	        shoppingcart.setUser(user);
	        ShoppingCart newCart = bshop.save(shoppingcart);
	        
	        return newCart;
	    }

	}

	@Override
	public Optional<Favorites> addProductToFavorite(Favorites favorites,Long userId) {
		// TODO Auto-generated method stub

		Optional<Favorites> favo = bfav.findById(userId);

		if (favo.isPresent()) {
			bfav.save(favorites);
			return favo;
		} else {
			bfav.save(favorites);
			return favo;
		}

	}

	@Override
	public List<FavoritesProjection> viewFavoriteProducts(Long userId) {
		// TODO Auto-generated method stub
		
		return bfav.getFavoritesByUserId(userId);
	}

	@Override
	public Boolean deleteProductFromCart( Long cartId) {
		// TODO Auto-generated method stub
		Optional<ShoppingCart> cartItem = bshop.findCartByUserIdAndProductId(cartId);
		if (cartItem.isPresent()) {
			bshop.delete(cartItem.get());
			return true;
		} else {
			return false;

		}
	}

	@Override
	public Boolean deleteProductFromFavorite(Long userId, Long productId) {
		// TODO Auto-generated method stub
		Optional<Favorites> favItem = bfav.findItemByUserIdAndProductId(userId, productId);
		if (favItem.isPresent()) {
			bfav.delete(favItem.get());
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<ShoppingCartProjection> updateCartQuantity(Long userId, Long productId, Long newQuantity) {
		// TODO Auto-generated method stub
		Optional<ShoppingCart> cartItemOpt = bshop.findCartItem(userId, productId);

		if (cartItemOpt.isPresent()) {
			ShoppingCart cartItem = cartItemOpt.get();
			cartItem.setQuantity(newQuantity);
			double unitPrice = cartItem.getProduct().getPrice();
			cartItem.setTotalPrice(unitPrice * newQuantity);
			bshop.save(cartItem);
			return bshop.findCartByUserId(userId);
		}
		else
		{
			return bshop.findCartByUserId(userId);
		}
	}

	@Override
	public User getUser(long uid) {
		// TODO Auto-generated method stub
		Optional<User> pp=buser.findById(uid);
		return pp.get();
	}

	@Override
	public Products getProduct(long pid) {
		// TODO Auto-generated method stub
		Optional<Products> pp=bproduct.findById(pid);
		return pp.get();
	}

	@Override
	public List<ShoppingCartProjection> viewCartProducts(Long userId) {
		List<ShoppingCartProjection> cartItems = bshop.findCartByUserId(userId);
		return cartItems;

	}

}
