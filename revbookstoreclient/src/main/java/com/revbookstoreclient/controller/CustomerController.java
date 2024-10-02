package com.revbookstoreclient.controller;


import java.util.Arrays;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.buyerservice.dto.Complaint;
import com.buyerservice.dto.FavoritesProjection;
import com.buyerservice.dto.ShoppingCartProjection;
import com.revbookstoreclient.dto.Favorites;
import com.revbookstoreclient.dto.Products;
import com.revbookstoreclient.dto.Review;
import com.revbookstoreclient.dto.ShoppingCart;
import com.revbookstoreclient.dto.User;



@Controller
@RequestMapping("/buyer")
public class CustomerController {
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@RequestMapping("/addProductsToCarts")
	public ModelAndView addProductsToCarts(HttpServletRequest request, HttpServletResponse response)
	{
				
		ModelAndView mv = new ModelAndView();
		
		long productId = Long.parseLong(request.getParameter("productId"));
		String discount_Price = request.getParameter("discountPrice");
		long discountPrice = Long.parseLong(discount_Price);
		String total_Price = request.getParameter("totalPrice");
		long totalPrice = Long.parseLong(total_Price);
		String productName = request.getParameter("productName");
		String productDescription = request.getParameter("productDescription");
		HttpSession hs = request.getSession();
		ShoppingCart addToCart=null;
		if((String) hs.getAttribute("name") == null)
		{
			mv.setViewName("login.jsp");
		}else
		{
			long userId = (long) hs.getAttribute("userId");
			
			List<ServiceInstance> instances1 = discoveryClient.getInstances("RETAILERSERVICE");
			ServiceInstance serviceInstance1 = instances1.get(0);

			String baseUrl1 = serviceInstance1.getUri().toString(); // return http://localhost:8080

			baseUrl1 = baseUrl1 + "/buyer/getUser/"+userId;
			
			RestTemplate restTemplate2 = new RestTemplate();
			User user = restTemplate2.getForObject(baseUrl1, User.class);
			
			//User uu= new User();
			List<ServiceInstance> instances2 = discoveryClient.getInstances("RETAILERSERVICE");
			ServiceInstance serviceInstance2 = instances2.get(0);

			String baseUrl2 = serviceInstance2.getUri().toString(); // return http://localhost:8080

			baseUrl2 = baseUrl2 + "/buyer/getProduct/"+productId;
			
			RestTemplate restTemplate1 = new RestTemplate();
			Products product = restTemplate1.getForObject(baseUrl1, Products.class);
			
			ShoppingCart cart = new ShoppingCart();
			cart.setCartId(1);
			cart.setProduct(product);
			cart.setUser(user);
			cart.setProductDescription(productDescription);
			cart.setProductName(productName);
			cart.setQuantity(1);
			cart.setTotalPrice(totalPrice);
//			cart.setDiscountPrice(discountPrice);
			
			
			List<ServiceInstance> instances = discoveryClient.getInstances("RETAILERSERVICE");
			ServiceInstance serviceInstance = instances.get(0);

			String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080

			baseUrl = baseUrl + "/AddProductToCart"+userId;
			
			RestTemplate restTemplate = new RestTemplate();
			addToCart = restTemplate.postForObject(baseUrl, cart, ShoppingCart.class);
		}
		
		if(addToCart!=null)
		{
			mv.addObject("products", addToCart);
			mv.setViewName("/cart.jsp");
		}
		else 
		{
			mv.addObject("message", "Failed to add product to cart");
			mv.setViewName("index.jsp");
		}
		return mv;
		
	}
	
	@RequestMapping("/addProductToFavorite")
	public ModelAndView addProductToFavorite(HttpServletRequest request,HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView();
		
		long productId = Long.parseLong(request.getParameter("productId"));
		String discount_Price = request.getParameter("discountPrice");
		long discountPrice = Long.parseLong(discount_Price);
		String total_Price = request.getParameter("totalPrice");
		long totalPrice = Long.parseLong(total_Price);
		String productName = request.getParameter("productName");
		String productDescription = request.getParameter("productDescription");
		HttpSession hs = request.getSession();
		
//		long userId = (long) hs.getAttribute("userId");
		long userId =1l;
		
		List<ServiceInstance> instances1 = discoveryClient.getInstances("RETAILERSERVICE");
		ServiceInstance serviceInstance1 = instances1.get(0);

		String baseUrl1 = serviceInstance1.getUri().toString(); // return http://localhost:8080

		baseUrl1 = baseUrl1 + "/buyer/getUser/"+userId;
		
		RestTemplate restTemplate2 = new RestTemplate();
		User user = restTemplate2.getForObject(baseUrl1, User.class);
		
		List<ServiceInstance> instances2 = discoveryClient.getInstances("RETAILERSERVICE");
		ServiceInstance serviceInstance2 = instances2.get(0);

		String baseUrl2 = serviceInstance2.getUri().toString(); // return http://localhost:8080

		baseUrl2 = baseUrl2 + "/buyer/getProduct"+productId;
		
		RestTemplate restTemplate1 = new RestTemplate();
		Products product = restTemplate1.getForObject(baseUrl1, Products.class);
		
		Favorites fav = new Favorites();
		fav.setProductName(productName);
		fav.setProductDescription(productDescription);
		fav.setPrice(totalPrice);
		fav.setDiecountPrice(discountPrice);
		fav.setProduct(product);
		fav.setUser(user);
		
		List<ServiceInstance> instances = discoveryClient.getInstances("RETAILERSERVICE");
		ServiceInstance serviceInstance = instances.get(0);

		String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080

		baseUrl = baseUrl + "/buyer/addProductToFavorite/" + userId;
		
		RestTemplate restTemplate = new RestTemplate();
		String addToFav = restTemplate.postForObject(baseUrl, fav, String.class);
		
		if(addToFav == "Item added successfully")
		{
			
			mv.addObject("message", "Item added successfully");
			mv.setViewName("products.jsp");
		}
		else
		{
			mv.addObject("message", "Failed to add item");
			mv.setViewName("products.jsp");
		}			
		return mv;
	}
	
	@RequestMapping("/cart")
	public ModelAndView viewCartProducts(HttpServletRequest request,HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView();
		HttpSession hs = request.getSession();
//		long userId = (long) hs.getAttribute("userId");
		long userId =1l;
		
		List<ServiceInstance> instances = discoveryClient.getInstances("BUYERSERVICE");
		ServiceInstance serviceInstance = instances.get(0);

		String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080

		baseUrl = baseUrl + "/buyer/cart/" + userId;
		
		RestTemplate restTemplate = new RestTemplate();
		List<ShoppingCartProjection> cartItems = restTemplate.getForObject(baseUrl, List.class);
		System.out.println(cartItems);
		if(cartItems != null)
		{
			mv.addObject("products",cartItems);
			mv.setViewName("/cart.jsp");
		}
		else
		{
			mv.addObject("error", "Product not found in cart.");
			mv.setViewName("/cart.jsp");
		}
		
		return mv;
	}
	
	@RequestMapping("/wishlist")
	public ModelAndView viewFavorite(HttpServletRequest request,HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView();
		HttpSession hs = request.getSession();
//		long userId = (long) hs.getAttribute("userId");
		long userId =1l;
		List<ServiceInstance> instances = discoveryClient.getInstances("BUYERSERVICE");
		ServiceInstance serviceInstance = instances.get(0);

		String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080

		baseUrl = baseUrl + "/buyer/wishlist/" + 1l;
		
		RestTemplate restTemplate = new RestTemplate();
		List<FavoritesProjection> favorites = restTemplate.getForObject(baseUrl, List.class);
		System.out.println(favorites);
		
		if(favorites != null)
		{
			mv.addObject("fav_list",favorites);
			mv.setViewName("/favorites.jsp");
		}
		else
		{
			mv.addObject("error", "No Product");
			mv.setViewName("/favorites.jsp");
		}
		
		return mv;	
	}
	
	@RequestMapping("/removeProductFromCart")
	public ModelAndView removeProductFromCart(HttpServletRequest request,HttpServletResponse response,@RequestParam("cartId") Long cartId)
	{
		ModelAndView mv = new ModelAndView();
		HttpSession hs = request.getSession();
		
//		long userId = (long) hs.getAttribute("userId");
		long userId = 1l;
		System.out.println(cartId);
		System.out.println(userId);
		List<ServiceInstance> instances = discoveryClient.getInstances("BUYERSERVICE");
		ServiceInstance serviceInstance = instances.get(0);

		String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080

		baseUrl = baseUrl + "/buyer/deleteProductFromCart/" + userId +"/" + cartId;
		
		RestTemplate restTemplate = new RestTemplate();
		 ResponseEntity<Object> removed = restTemplate.exchange(baseUrl, HttpMethod.DELETE, null, Object.class);
		 
		 List<ServiceInstance> instances1 = discoveryClient.getInstances("BUYERSERVICE");
			ServiceInstance serviceInstance1 = instances1.get(0);

			String baseUrl1 = serviceInstance.getUri().toString(); // return http://localhost:8080

			baseUrl = baseUrl1 + "/buyer/cart/" + userId;
			
			RestTemplate restTemplate1 = new RestTemplate();
			List<ShoppingCartProjection> cartItems = restTemplate1.getForObject(baseUrl, List.class);
			System.out.println(cartItems);
			if(cartItems != null)
			{
				mv.addObject("products",cartItems);
				mv.setViewName("/cart.jsp");
			}
			else
			{
				mv.addObject("error", "Product not found in cart.");
				mv.setViewName("/cart.jsp");
			}
			
			return mv;
	}
	
	@RequestMapping("/removeFromFavorite")
	public ModelAndView removeFromFavorite(HttpServletRequest request,HttpServletResponse response,@RequestParam("favoriteId") Long favoriteId)
	{
		ModelAndView mv = new ModelAndView();
		HttpSession hs = request.getSession();
		
//		Long userId = (long) hs.getAttribute("userId");
		long userId =1l;
		System.out.println(favoriteId);
		List<ServiceInstance> instances = discoveryClient.getInstances("BUYERSERVICE");
		ServiceInstance serviceInstance = instances.get(0);

		String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080

		baseUrl = baseUrl + "/buyer/deleteProductFromFavorite/" + userId + "/" + favoriteId;


		
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Void> entity = new HttpEntity<>(null); // Create an empty entity for DELETE request
		ResponseEntity<String> response1 = restTemplate.exchange(baseUrl, HttpMethod.DELETE, entity, String.class);
		Object removed = response1.getBody();

		
		
		//viewing the favorites
		List<ServiceInstance> instances1 = discoveryClient.getInstances("BUYERSERVICE");
		ServiceInstance serviceInstance1 = instances1.get(0);

		String baseUrl1 = serviceInstance1.getUri().toString(); // return http://localhost:8080

		baseUrl = baseUrl1 + "/buyer/wishlist/" + 1l;
		
		RestTemplate restTemplate1 = new RestTemplate();
		List<FavoritesProjection> favorites = restTemplate1.getForObject(baseUrl, List.class);
		System.out.println(favorites);
		
		if(favorites != null)
		{
			mv.addObject("fav_list",favorites);
			mv.setViewName("/favorites.jsp");
		}
		else
		{
			mv.addObject("error", "No Product");
			mv.setViewName("/favorites.jsp");
		}
		
		return mv;
	}
	
	@RequestMapping("/updateQuantity")
	public ModelAndView updateQuantity(HttpServletRequest request,HttpServletResponse response,@RequestParam("quantity") String quantity,@RequestParam("cartId") Long cartId)
	{
		ModelAndView mv = new ModelAndView();
		HttpSession hs = request.getSession();
//		Long userId = (long) hs.getAttribute("userId");
		long userId =1l;
		long quantity1 = Long.parseLong(quantity);
		
		List<ServiceInstance> instances = discoveryClient.getInstances("BUYERSERVICE");
		ServiceInstance serviceInstance = instances.get(0);

		String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080

		baseUrl = baseUrl + "/buyer/updateQuantity/" + userId +"/"+ cartId +"/"+ quantity1;
		
		RestTemplate restTemplate = new RestTemplate();
		 ResponseEntity<Object> responseEntity = restTemplate.exchange(
			        baseUrl, HttpMethod.PUT, null, Object.class);
		
		List<ServiceInstance> instances1 = discoveryClient.getInstances("BUYERSERVICE");
		ServiceInstance serviceInstance1 = instances1.get(0);

		String baseUrl1 = serviceInstance.getUri().toString(); // return http://localhost:8080

		baseUrl = baseUrl1 + "/buyer/cart/" + userId;
		
		RestTemplate restTemplate1 = new RestTemplate();
		List<ShoppingCartProjection> cartItems = restTemplate1.getForObject(baseUrl, List.class);
		System.out.println(cartItems);
		if(cartItems != null)
		{
			mv.addObject("products",cartItems);
			mv.setViewName("/cart.jsp");
		}
		else
		{
			mv.addObject("error", "Product not found in cart.");
			mv.setViewName("/cart.jsp");
		}
		
		return mv;
		
	}
	
	
	
	
	//Bhannu Section
	
	@RequestMapping("/submitreview")
	public ModelAndView submitreview(HttpServletRequest request,HttpServletResponse response,@RequestParam("productId") String productId,@RequestParam("reviewText") String reviewtext,@RequestParam("rating") String rating,HttpSession session) {
		long pid=Long.parseLong(productId);
		System.out.println("prodid "+pid);
		int rate=Integer.parseInt(rating);
//		long userId = (long) session.getAttribute("userId");
		long userId = 1;

		
//		User user = new User();
//		user.setUserId(1L);
		
//		System.out.println(user.getUserId());
		Products product = new Products();
		product.setProductId(10);
		Review review=new Review();
		review.setProduct(product);
		review.setRating(rate);
		review.setReviewText(reviewtext);
		
		review.setUserId(userId);
		//System.out.println("in client "+review.getUser().getUserId());
		
		List<ServiceInstance> instance2=discoveryClient.getInstances("BUYERSERVICE");
		ServiceInstance serviceInstance2=instance2.get(0);
	
		String baseUrl2=serviceInstance2.getUri().toString(); //return http://localhost:8080
		
	
		baseUrl2=baseUrl2+"/buyer/submitreview";
		System.out.println(baseUrl2);
		RestTemplate rs = new RestTemplate();

		String addToFav = rs.postForObject(baseUrl2, review, String.class);
		System.out.println(addToFav);
	
		List<ServiceInstance> instances=discoveryClient.getInstances("BUYERSERVICE");
		ServiceInstance serviceInstance=instances.get(0);	
		String baseUrl=serviceInstance.getUri().toString(); //return http://localhost:8080
		baseUrl=baseUrl+"/buyer/myreviews/"+1L;
		
		

		RestTemplate restTemplate=new RestTemplate();
		List<Review> userreviews  = restTemplate.getForObject(baseUrl, List.class);
				//.getBody();
		System.out.println(userreviews);
      ModelAndView mv=new ModelAndView();
      mv.addObject("userreviews",userreviews);
      mv.setViewName("/reviews.jsp");
      return mv;

}
	
	@RequestMapping("/submitcomplaint")
	public ModelAndView submitcomplaint(HttpServletRequest request,HttpServletResponse response,@RequestParam("username") String username,@RequestParam("complaintText") String comtext) {
		List<ServiceInstance> instances1=discoveryClient.getInstances("BUYERSERVICE");
		ServiceInstance serviceInstance1=instances1.get(0);
		
		String baseUrl1=serviceInstance1.getUri().toString(); //return http://localhost:8080
		
		baseUrl1=baseUrl1+"/viewuser/"+ 1L;
		RestTemplate restTemplate=new RestTemplate();

		
	  User user =restTemplate.getForObject(baseUrl1,User.class);
		Complaint complaint=new Complaint();
		complaint.setUserName(username);
		complaint.setUser(user);
		complaint.setComplaintText(comtext);
		
	List<ServiceInstance> instances=discoveryClient.getInstances("BUYERSERVICE");
	ServiceInstance serviceInstance=instances.get(0);
	
	String baseUrl=serviceInstance.getUri().toString(); //return http://localhost:8080
	
	baseUrl=baseUrl+"/submitcomplaint";
	
	

	

	HttpHeaders headers = new HttpHeaders();
	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

	HttpEntity<String> entity = new HttpEntity<String>(headers);

	ResponseEntity<Object> str = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, Object.class);
			//.getBody();
	int i=0;
	ModelAndView mv= new ModelAndView();
	String message="success";
	mv.addObject("message",i);

	return mv;
}
	
	@RequestMapping("/myreviews")
	public ModelAndView myreviews(HttpServletRequest request,HttpServletResponse response) {
		
		
		List<ServiceInstance> instances=discoveryClient.getInstances("BUYERSERVICE");
		ServiceInstance serviceInstance=instances.get(0);
		
		String baseUrl=serviceInstance.getUri().toString(); //return http://localhost:8080
		
		baseUrl=baseUrl+"/buyer/myreviews/"+1L;
		
		

		RestTemplate restTemplate=new RestTemplate();

		

		List<Review> userreviews  = restTemplate.getForObject(baseUrl, List.class);
				//.getBody();
		System.out.println(userreviews);
      ModelAndView mv=new ModelAndView();
      mv.addObject("userreviews",userreviews);
      mv.setViewName("/reviews.jsp");
      return mv;
}
	
	
	@RequestMapping("/reviews")
	public ModelAndView reviews(HttpServletRequest request,HttpServletResponse response,@RequestParam("reviewId") String pid) {
		
		long prid=Long.parseLong(pid);
		List<ServiceInstance> instances=discoveryClient.getInstances("BUYERSERVICE");
		ServiceInstance serviceInstance=instances.get(0);
		
		String baseUrl=serviceInstance.getUri().toString(); //return http://localhost:8080
		
		baseUrl=baseUrl+"/buyer/reviews/"+prid;
		
		

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Void> entity = new HttpEntity<>(null); // Create an empty entity for DELETE request
		ResponseEntity<String> response1 = restTemplate.exchange(baseUrl, HttpMethod.DELETE, entity, String.class);
		Object removed = response1.getBody();
				//.getBody();
		
		//viewing the reviews again
		List<ServiceInstance> instances1=discoveryClient.getInstances("BUYERSERVICE");
		ServiceInstance serviceInstance1=instances1.get(0);
		
		String baseUrl1=serviceInstance1.getUri().toString(); //return http://localhost:8080
		
		baseUrl=baseUrl1+"/buyer/myreviews/"+1L;
		
		

		RestTemplate restTemplate1=new RestTemplate();

		

		List<Review> userreviews  = restTemplate1.getForObject(baseUrl, List.class);
				//.getBody();
		System.out.println(userreviews);
      ModelAndView mv=new ModelAndView();
      mv.addObject("userreviews",userreviews);
      mv.setViewName("/reviews.jsp");
      return mv;
      
}

}
