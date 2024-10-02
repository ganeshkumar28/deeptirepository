package com.buyerservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buyerservice.dao.FavoritesInterface;
import com.buyerservice.dao.OrdersInterface;
import com.buyerservice.dao.ProductRepository;
import com.buyerservice.dao.ReviewInterface;
import com.buyerservice.dao.ShoppingcartInterface;
import com.buyerservice.dao.UserInterface;
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

@Service
@Transactional
public class BuyerService implements BuyerServiceInterface {

	@Autowired
	private ShoppingcartInterface bshop;

	@Autowired
	private FavoritesInterface bfav;
	
	@Autowired
	private ReviewInterface brev;
	
	@Autowired
	private UserInterface buser;
	
	@Autowired
	private OrdersInterface bord;
	
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
	public List<ShoppingCartProjection> updateCartQuantity(Long userId, Long cartId, Long newQuantity) {
		// TODO Auto-generated method stub
		Optional<ShoppingCart> cartItemOpt = bshop.findCartItem(userId, cartId);

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

	@Override
	public Boolean deleteProductFromFavorite(Long favoriteId) {
		Optional<Favorites> favItem = bfav.findItemByUserIdAndProductId(favoriteId);
		if (favItem.isPresent()) {
			bfav.delete(favItem.get());
			return true;
		} else {
			return false;
		}
	}
	
	
	
	
	//Bhanu Section

	@Override
	public int submitreview(Review review) {
		// TODO Auto-generated method stub
		int i=0;
		brev.save(review);
		i=1;
		return i;
	}
	
	

	@Override
	public int submitorder(Orders order) {
		// TODO Auto-generated method stub
		int i=0;
		 bord.save(order);
		 i=1;
		 return i;
	}

	@Override
	public Boolean deleteReviewById(Long prid) {
		// TODO Auto-generated method stub
		
		Optional<Review> review = brev.findReviewByProductId(prid);
		if (review.isPresent()) {
			brev.delete(review.get());
			return true;
		} else {
			return false;
		}

	}

	@Override
	public List<ReviewProjection> getAllreviews(Long userId) {
		// TODO Auto-generated method stub
		return brev.findbyuserid(userId);
	}

	@Override
	public Products getproductByUserId(Long pid) {
		Optional<Products> pp=bproduct.findById(pid);
		return pp.get();
	}

	@Override
	public User getByUserId(Long uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserProjection login(String email, String password) {
		UserProjection user=buser.login(email,password);
		return user;
	}

//	@Override
//	public User getByUserId(Long uid) {
//		Optional<User> pp=buser.getByUserId1(uid);
//		return pp.get();
//	}

}
