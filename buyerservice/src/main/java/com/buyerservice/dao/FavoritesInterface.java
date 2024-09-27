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


	@Query("select f from Favorites f join f.product p where f.user.userId = :userId and p.productId = :productId")
	Optional<Favorites> findItemByUserIdAndProductId(Long userId, Long productId);

}