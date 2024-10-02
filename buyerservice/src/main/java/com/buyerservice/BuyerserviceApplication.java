package com.buyerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.netflix.discovery.DiscoveryManager;

@SpringBootApplication
@CacheEvict
public class BuyerserviceApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(BuyerserviceApplication.class, args);
	}

}
