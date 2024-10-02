package com.revbookstoreclient.controller;





import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.revbookstoreclient.dto.Products;
import com.revbookstoreclient.dto.ProductsProjection;
import com.revbookstoreclient.dto.Review;
import com.revbookstoreclient.dto.Orders;




@Controller

@RequestMapping("product")
public class SellerController {
	
	@Autowired
	private DiscoveryClient discoveryclient;
	
	@PostMapping("addProduct")
	public ModelAndView addProduct(@RequestParam("name") String name,
			                       @RequestParam("description") String description,
			                       @RequestParam("category") String category,
			                       @RequestParam("price") String price,
			                       @RequestParam("discount_price") String discount_price,
			                       @RequestParam("quantity") String quantity,
			                       @RequestParam("imageUrl") String imageUrl,
			                       HttpSession session) {
		System.out.println(name);
		System.out.println(description);
		System.out.println(category);
		System.out.println(price);
		System.out.println(imageUrl);
//		Long userId = (Long) session.getAttribute("userId");
		long userId = 1;
		ModelAndView mv = new ModelAndView();
		//if user-id is null 
//		if(userId == null) {
//			mv.setViewName("login.jsp");
//			return mv;
//		}
		
		double parsedPrice;
		try {
			parsedPrice = Double.parseDouble(price);
		}catch(NumberFormatException e) {
			String err = "invalid price format";
			mv.addObject("error",err);
			mv.setViewName("add.jsp");
			return mv;
		}
		
		Products product = new Products();
		product.setProductName(name);
		product.setProductDescription(description);
		product.setProductCategory(category);
		product.setPrice(parsedPrice);
		
		if(discount_price!=null) {
			try {
				double discountPrice = Double.parseDouble(discount_price);
				product.setDiscountPrice(discountPrice);
				}catch(NumberFormatException e) {
					String err = "invalid price format";
					mv.addObject("error",err);
					mv.setViewName("add.jsp");
					return mv;
				}
		}
		Long parsedQuantity;
		try {
			parsedQuantity = Long.parseLong(quantity);
			product.setQuantity(parsedQuantity);
			}catch(NumberFormatException e) {
				String err = "invalid quantity format";
				mv.addObject("error",err);
				mv.setViewName("add.jsp");
				return mv;
			}
		product.setImageUrl(imageUrl);
		product.setUserId(1L);
		
		List<ServiceInstance> instances =discoveryclient.getInstances("SELLERSERVICE");
		ServiceInstance serviceInstance = instances.get(0);
		
		String baseUrl = serviceInstance.getUri().toString();
		baseUrl = baseUrl + "/product/addProduct/" +1L;
		
		RestTemplate rest = new RestTemplate();
		Products productAdded = rest.postForObject(baseUrl, product, Products.class);
		if(productAdded != null) {
			List<ServiceInstance> instances1 =discoveryclient.getInstances("SELLERSERVICE");
			ServiceInstance serviceInstance1 = instances1.get(0);
			
			String baseUrl1 = serviceInstance1.getUri().toString();
			baseUrl = baseUrl1 + "/product/viewProducts/" +1L;
			
			RestTemplate rest1 = new RestTemplate();
			List<ProductsProjection> products = rest.getForObject(baseUrl1, List.class);
			mv.addObject("product_list",products);
			mv.setViewName("/seller-views/inventory.jsp");
			return mv;
			
		}else {
			String err = "Something went wrong";
			mv.addObject("error",err);
			mv.setViewName("add.jsp");
			return mv;
		}
		
}
	

	@RequestMapping("viewProducts")
	public ModelAndView viewProducts(HttpSession session) {
//		Long userId = (Long) session.getAttribute("userId");
		long userId =1;
		List<ServiceInstance> instances =discoveryclient.getInstances("SELLERSERVICE");
		ServiceInstance serviceInstance = instances.get(0);
		
		String baseUrl = serviceInstance.getUri().toString();
		baseUrl = baseUrl + "/viewProducts/" +1;
		
		RestTemplate rest = new RestTemplate();
		List<Products> products = rest.getForObject(baseUrl, List.class);
		ModelAndView mv = new ModelAndView();
		mv.addObject("product_list",products);
		mv.setViewName("/seller-views/inventory.jsp");
		return mv;
	}
	
	@RequestMapping("editProducts")
	public ModelAndView editProducts(
			@RequestParam("productId") String productId,
			@RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("price") String price,
            @RequestParam("discount_price") String discount_price,
            @RequestParam("quantity") String quantity,
            @RequestParam("imageUrl") String imageUrl,HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		ModelAndView mv = new ModelAndView();
		Products product = new Products();
		product.setProductName(name);
		product.setProductDescription(description);
		product.setProductCategory(category);
		product.setUserId(userId);
		
		double parsedPrice;
		parsedPrice = Double.parseDouble(price);
		product.setPrice(parsedPrice);
		
		double discountPrice = Double.parseDouble(discount_price);
		product.setDiscountPrice(discountPrice);
		
		long parsedQuantity = Long.parseLong(quantity);
		product.setQuantity(parsedQuantity);
		product.setImageUrl(imageUrl);
		
		List<ServiceInstance> instances =discoveryclient.getInstances("SELLERSERVICE");
		ServiceInstance serviceInstance = instances.get(0);
		
		String baseUrl = serviceInstance.getUri().toString();
		baseUrl = baseUrl + "/editProducts/" +productId;
		
		RestTemplate rest = new RestTemplate();
		Products productEdited = rest.postForObject(baseUrl, product, Products.class);
		if(productEdited!=null) {
			List<ServiceInstance> instances1 =discoveryclient.getInstances("SELLERSERVICE");
			ServiceInstance serviceInstance1 = instances1.get(0);
			
			String baseUrl1 = serviceInstance1.getUri().toString();
			baseUrl = baseUrl1 + "/viewProducts/" +userId;
			
			RestTemplate rest1 = new RestTemplate();
			List<Products> products = rest1.getForObject(baseUrl, List.class);
			mv.addObject("edit_product",products);
			mv.setViewName("/seller-views/inventory.jsp");
			return mv;
			
		}else {
			String err = "Something went wrong";
			mv.addObject("error",err);
			mv.setViewName("add.jsp");
			return mv;
		}
		
	}
	
	
		
}
