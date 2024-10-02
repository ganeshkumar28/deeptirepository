package com.adminservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.adminservice.dao.ComplaintRepository;
import com.adminservice.dao.OrdersRepository;
import com.adminservice.dao.UserRepository;
import com.adminservice.dto.ComplaintProjection;
import com.adminservice.dto.UserProjection;
import com.adminservice.entity.Complaint;
import com.adminservice.entity.Orders;
import com.adminservice.entity.Products;
import com.adminservice.entity.User;

public class AdminServiceClass implements AdminServiceInterface {

	
	@Autowired
	private UserRepository userdao;
	
	@Autowired
	private ComplaintRepository complaintdao;
	
	@Autowired
	private OrdersRepository orderdao;
	

	
	@Override
	public List<UserProjection> viewSellers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserProjection> viewBuyers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComplaintProjection> viewComplaintOnSeller() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int login(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int register(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int activateUser(Long userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int blockUser(Long userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Complaint> getAllProducts() {
		return complaintdao.findAll();
	
	}

	@Override
	public List<Orders> getAllOrderes() {
		// TODO Auto-generated method stub
		return orderdao.findAll();
	}

	@Override
	public List<User> getAllBuyer() {
		// TODO Auto-generated method stub
		String str = "buyer";
		List<User> buyer = userdao.findbuyer(str);
		return buyer;
	}

	@Override
	public List<User> getAllSeller() {
		String str = "seller";
		List<User> seller = userdao.findseller(str);
		return seller;
	}

}
