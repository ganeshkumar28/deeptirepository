package com.retailerservice.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.retailerservice.dto.ShoppingCartProjection;
import com.retailerservice.entity.ShoppingCart;
import com.retailerservice.entity.User;

@Repository
public interface ShoppingcartInterface extends JpaRepository<ShoppingCart,Long>{

	@Query("select sc from ShoppingCart sc where sc.user = :user")
	List<ShoppingCart> findCartById(User user);

	@Query("select sc from ShoppingCart sc where sc.user.userId = :userId")
	List<ShoppingCartProjection> findCartByUserId(long userId);

	@Query("select sc from ShoppingCart sc where sc.user.userId = :userId")
	Optional<ShoppingCart> findCartByUserID(long userId);

	@Query("select sc from ShoppingCart sc where sc.user.userId = :userId and sc.product.productId = :productId")
	Optional<ShoppingCart> findCartByUserIdAndProductId(Long userId, Long productId);

	@Query("select sc from ShoppingCart sc where sc.user.userId = :userId and sc.product.productId = :productId")
	Optional<ShoppingCart> findCartItem(Long userId, Long productId);

	
}
