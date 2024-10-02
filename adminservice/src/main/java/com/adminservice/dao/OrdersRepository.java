package com.adminservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adminservice.entity.Complaint;
import com.adminservice.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders,Long> {

}
