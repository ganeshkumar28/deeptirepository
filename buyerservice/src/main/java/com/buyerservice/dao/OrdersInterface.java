package com.buyerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buyerservice.entity.Orders;

public interface OrdersInterface extends JpaRepository<Orders, Long>{

}
