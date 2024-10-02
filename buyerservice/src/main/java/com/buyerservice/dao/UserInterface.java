package com.buyerservice.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.buyerservice.dto.UserProjection;
import com.buyerservice.entity.User;

@Repository
public interface UserInterface extends JpaRepository<User,Long>{

	@Query("select u from User u where u.userId = :userId")
	User getByUserId(Long userId);

	@Query(nativeQuery = true, value = "select * from user where email = :email and password = :password")
	UserProjection login(@Param("email") String email, @Param("password") String password);


}