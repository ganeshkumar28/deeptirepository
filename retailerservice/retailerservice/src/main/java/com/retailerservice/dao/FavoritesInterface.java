package com.retailerservice.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.retailerservice.dto.FavoritesProjection;
import com.retailerservice.entity.Favorites;

@Repository
public interface FavoritesInterface extends JpaRepository<Favorites,Long>{

	@Query("select f from Favorites f where f.user.userId = :userId")
	Optional<FavoritesProjection> getFavoritesByUserId(Long userId);

	@Query("select f from Favorites f join f.product p where f.user.userId = :userId and p.productId = :productId")
	Optional<Favorites> findItemByUserIdAndProductId(Long userId, Long productId);

}
