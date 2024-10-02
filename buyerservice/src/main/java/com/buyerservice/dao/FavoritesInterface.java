package com.buyerservice.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.buyerservice.dto.FavoritesProjection;
import com.buyerservice.entity.Favorites;

@Repository
public interface FavoritesInterface extends JpaRepository<Favorites,Long>{

	@Query(nativeQuery = true, value = "SELECT * FROM favorites f WHERE f.user_user_id = :userId")
	List<FavoritesProjection> getFavoritesByUserId(@Param("userId") long userId);


//	@Query(nativeQuery = true, value ="select * from favorites f where f.favorite_id = :favoriteId and f.user_user_id = :userId")
//	Optional<Favorites> findItemByUserIdAndProductId(@Param("userId") long userId, @Param("favoriteId") long favoriteId);

	
	@Query(nativeQuery = true, value ="select * from favorites f where f.favorite_id = :favoriteId ")
	Optional<Favorites> findItemByUserIdAndProductId(@Param("favoriteId") long favoriteId);


}