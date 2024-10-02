package com.sellerservice.dao;

import java.util.Optional;
import java.util.List;
import com.sellerservice.dto.ProductsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sellerservice.entity.Products;
import com.sellerservice.entity.User;
@Repository
public interface SellerDaoInterface extends JpaRepository<Products,Long>{



	@Query("select u from User u where u.userId = :userId")
	Optional<User> findUserById(long userId);
	
	@Query(nativeQuery = true, value= "select * from products  where seller_id = :userId")
	List<ProductsProjection> findProductByUserId(@Param("userId") long userId);

	@Query(nativeQuery = true, value= "select * from products")
	List<ProductsProjection> findProducts();

}