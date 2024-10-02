package com.revbookstoreclient.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.buyerservice.dto.UserProjection;
import com.revbookstoreclient.dto.Products;
import com.revbookstoreclient.dto.User;

@Controller
@RequestMapping("/user")
public class LoginSignupController {
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@RequestMapping("/register")
	public ModelAndView adminRegister(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("name") String name,@RequestParam("phone_number") String phoneNumber,
			@RequestParam("address") String address,@RequestParam("pincode") String pincode,
			@RequestParam("userType") String userType)
	{
		ModelAndView mv = new ModelAndView();
		if("admin".equals(userType))
		{
			String status = "active";
			User user = new User();
			user.setName(name);
			user.setPassword(password);
			user.setPhoneNumber(phoneNumber);
			user.setPincode(pincode);
			user.setEmail(email);
			user.setAddress(address);
			user.setUserType(userType);
			user.setStatus(status);
			
			String registered=null;
			if(userType.equals("admin"))
			{
				List<ServiceInstance> instances = discoveryClient.getInstances("ADMINSERVICE");
				ServiceInstance serviceInstance = instances.get(0);

				String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080
				baseUrl = baseUrl + "/admin/register";
				
				RestTemplate restTemplate = new RestTemplate();
				registered = restTemplate.postForObject(baseUrl, user,String.class);
			}
			else if(userType.equals("buyer"))
			{
				List<ServiceInstance> instances = discoveryClient.getInstances("BUYERSERVICE");
				ServiceInstance serviceInstance = instances.get(0);

				String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080
				baseUrl = baseUrl + "/buyer/register";
				
				RestTemplate restTemplate = new RestTemplate();
				registered = restTemplate.postForObject(baseUrl, user,String.class);
			}
			else
			{
				List<ServiceInstance> instances = discoveryClient.getInstances("RETAILERSERVICE");
				ServiceInstance serviceInstance = instances.get(0);

				String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080
				baseUrl = baseUrl + "/retailer/register";
				
				RestTemplate restTemplate = new RestTemplate();
				registered = restTemplate.postForObject(baseUrl, user,String.class);
			}
			
			
			if(!"Registered".equals(registered))
			{
				mv.addObject("errorMessage", "Registration failed.");
				mv.setViewName("signup.jsp");
			}
			else
			{
				mv.addObject("successMessage", "Registration successful.");
				mv.setViewName("signup.jsp");
			}
			
		}
		else {
            mv.addObject("errorMessage", "Admin service not available.");
            mv.setViewName("signup.jsp");
		}
		return mv;
		
	}
	
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response,@RequestParam("email") String email, @RequestParam("password") String password,HttpSession session)
	{
		System.out.println("em"+email);
		System.out.println("pss"+password);
		ModelAndView mv = new ModelAndView();
		HttpSession hs = request.getSession();
		
//		User user = new User();
//		user.setEmail((String)hs.getAttribute(email));
//		user.setPassword(password);
		List<ServiceInstance> instances=discoveryClient.getInstances("BUYERSERVICE");
		ServiceInstance serviceInstance=instances.get(0);
		String baseUrl=serviceInstance.getUri().toString(); //return http://localhost:8080
		baseUrl=baseUrl+"/buyer/login/"+email+"/"+password;
		System.out.println(baseUrl);
		
		RestTemplate restTemplate=new RestTemplate();
	
		User us =  restTemplate.getForObject(baseUrl,User.class);
		long userId = us.getUserId();
		System.out.println(us.getUserType());
		System.out.println(us.getStatus());
		
		if(us.getUserType().equals("buyer") && us.getStatus().equals("active"))
		{
			session.setAttribute("userId", us.getUserId());
			session.setAttribute("user-role", us.getUserType());
			session.setAttribute("name", us.getName());
			
			List<ServiceInstance> instances1=discoveryClient.getInstances("SELLERSERVICE");
			ServiceInstance serviceInstance1=instances1.get(0);
			String baseUrl1=serviceInstance1.getUri().toString(); //return http://localhost:8080
			baseUrl1=baseUrl1+"/product/getProduct";
			System.out.println(baseUrl1);
			
			RestTemplate rest = new RestTemplate();
			List<Products> products = rest.getForObject(baseUrl1, List.class);
			mv.addObject("product_list",products);
			mv.setViewName("/products.jsp");
			return mv;
		}
		else if(us.getUserType().equals("seller") && us.getStatus().equals("active"))
		{
			session.setAttribute("userId", us.getUserId());
			session.setAttribute("user-role", us.getUserType());
			session.setAttribute("name", us.getName());
			
			List<ServiceInstance> instances2=discoveryClient.getInstances("SELLERSERVICE");
			ServiceInstance serviceInstance2=instances2.get(0);
			String baseUrl2=serviceInstance2.getUri().toString(); //return http://localhost:8080
			baseUrl2=baseUrl2+"/product/viewProduct/"+userId;
			
			RestTemplate rest = new RestTemplate();
			List<Products> products = rest.getForObject(baseUrl2, List.class);
			mv.addObject("product_list",products);
			mv.setViewName("/seller-views/inventory.jsp");
			return mv;
		}
		else if(us.getUserType().equals("null")) {
			
			session.setAttribute("userId", us.getUserId());
			session.setAttribute("user-role", us.getUserType());
			session.setAttribute("name", us.getName());
			
			List<ServiceInstance> instances3=discoveryClient.getInstances("ADMINSERVICE");
			ServiceInstance serviceInstance3=instances3.get(0);
			String baseUrl3=serviceInstance3.getUri().toString(); //return http://localhost:8080
			baseUrl3=baseUrl3+"/admin/login";
			
			RestTemplate rest = new RestTemplate();
			List<Object> counts = rest.getForObject(baseUrl3, List.class);
			int noofcomplaint=(Integer)counts.get(1);
			int nooforder=(Integer)counts.get(2);
			int noofbuyer=(Integer)counts.get(3);
			int noofseller=(Integer)counts.get(3);	
			 if (counts.size()>0) {
		            hs.setAttribute("uname", email);
		            
		            mv.addObject("noofcomplaint", noofcomplaint);
		            mv.addObject("nooforder", nooforder);
		            mv.addObject("noofcustomer", noofbuyer);
		            mv.addObject("noofproduct", noofseller);
		            
		            mv.setViewName("/admin/adminDashboard.jsp");
		          

		        } else {
		            //If details are wrong
		            String message = "You have enter wrong credentials";
		            hs.setAttribute("credential", message);
		            //Redirecting admin to admin login page
		            mv.setViewName("/index.jsp");
		        }
				
				return mv;
			}
		else {
			mv.setViewName("/dumb.jsp");
		}
		
		
		
		return mv;
		
	}
	

}
