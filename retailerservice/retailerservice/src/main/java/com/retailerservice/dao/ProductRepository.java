package com.retailerservice.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.retailerservice.dto.ProductsProjection;
import com.retailerservice.entity.Products;
import com.retailerservice.entity.User;

@Repository
public interface ProductRepository extends JpaRepository<Products,Long>{

	@Query("select p from Products p where p.user.userId = :userId")
	Optional<Products> findByUserId(long userId);

	@Query("select u from User u where u.userId = :userId")
	Optional<User> findUserById(long userId);

	@Query("select p from Products p where p.user.userId = :userId")
	List<ProductsProjection> findProductByUserId(Long userId);

}
