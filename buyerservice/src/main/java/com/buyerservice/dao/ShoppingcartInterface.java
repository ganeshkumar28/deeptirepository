package com.buyerservice.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.buyerservice.dto.ShoppingCartProjection;
import com.buyerservice.entity.ShoppingCart;
import com.buyerservice.entity.User;

@Repository
public interface ShoppingcartInterface extends JpaRepository<ShoppingCart,Long>{

	@Query("select sc from ShoppingCart sc where sc.user = :user")
	List<ShoppingCart> findCartById(User user);

	@Query(nativeQuery = true, value ="select * from shopping_cart sc where sc.user_user_id = :userId")
	List<ShoppingCartProjection> findCartByUserId(@Param("userId") long userId);

	@Query("select sc from ShoppingCart sc where sc.user.userId = :userId")
	Optional<ShoppingCart> findCartByUserID(long userId);

	@Query(nativeQuery = true, value ="select * from shopping_cart sc where  sc.cart_id = :cartId")
	Optional<ShoppingCart> findCartByUserIdAndProductId(@Param("cartId") long cartId);

	@Query("select sc from ShoppingCart sc where sc.user.userId = :userId and sc.product.productId = :productId")
	Optional<ShoppingCart> findCartItem(Long userId, Long productId);

	
}
