package com.buyerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.buyerservice.entity.User;

@Repository
public interface UserInterface extends JpaRepository<User,Long>{

	@Query("select u from User u where u.userId = :userId")
	User getByUserId(Long userId);

}