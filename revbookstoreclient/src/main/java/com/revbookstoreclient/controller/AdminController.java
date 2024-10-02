package com.revbookstoreclient.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.buyerservice.dto.ComplaintProjection;
import com.revbookstoreclient.dto.User;
import com.buyerservice.dto.UserProjection;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@RequestMapping("/viewSellers")
	public ModelAndView viewSellers(HttpServletRequest request, HttpServletResponse response) {
	    ModelAndView mv = new ModelAndView();
	    
	    // Get the list of service instances for ADMINSERVICE
	    List<ServiceInstance> instances = discoveryClient.getInstances("ADMINSERVICE");
	    
	    if (instances != null && !instances.isEmpty()) {
	        // Proceed only if instances are available
	        ServiceInstance serviceInstance = instances.get(0);

	        // Construct the base URL for the target service
	        String baseUrl = serviceInstance.getUri().toString(); // e.g., http://localhost:8080
	        baseUrl = baseUrl + "/admin/viewSellers";
	        
	        // Fetch the seller list from the target service
	        RestTemplate restTemplate = new RestTemplate();
	        List<UserProjection> sellers = restTemplate.getForObject(baseUrl, List.class);
	        System.out.println(sellers);
	        
	        if (sellers != null && !sellers.isEmpty()) {
	            mv.addObject("seller_list", sellers);
	        } else {
	            mv.addObject("error", "No sellers found.");
	        }
	    } else {
	        // Handle the case where no service instances are found
	        mv.addObject("error", "ADMINSERVICE is currently unavailable.");
	    }

	    // Set the view to display
	    mv.setViewName("/admin/users.jsp");
	    return mv;
	}
	
	@RequestMapping("/viewBuyers")
	public ModelAndView viewBuyers(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView();
		
		List<ServiceInstance> instances = discoveryClient.getInstances("ADMINSERVICE");
		ServiceInstance serviceInstance = instances.get(0);

		String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080
		baseUrl = baseUrl + "/admin/viewBuyers";
		
		RestTemplate restTemplate = new RestTemplate();
		List<UserProjection> buyers = restTemplate.getForObject(baseUrl, List.class);
		System.out.println(buyers);
		
		if(buyers != null)
		{
			mv.addObject("buyer_list",buyers);
			mv.setViewName("/admin/buyers.jsp");
		}
		else
		{
			mv.addObject("error", "No Product");
			mv.setViewName("/admin/buyers.jsp");
		}
		
		return mv;
	}
	
	@RequestMapping("/viewComplaints")
	public ModelAndView viewComplaints(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView();
		
		
		List<ServiceInstance> instances = discoveryClient.getInstances("ADMINSERVICE");
		ServiceInstance serviceInstance = instances.get(0);

		String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080
		baseUrl = baseUrl + "/admin/viewComplaints";
		
		RestTemplate restTemplate = new RestTemplate();
		List<ComplaintProjection> complaints = restTemplate.getForObject(baseUrl, List.class);
		System.out.println(complaints);
		
	    if (complaints != null && !complaints.isEmpty()) 
		{
			mv.addObject("complaints",complaints);
			mv.setViewName("complaints.jsp");
		}
		else
		{
			mv.addObject("error", "No Product");
			mv.setViewName("complaints.jsp");
		}
		
		return mv;
	}
	
	@RequestMapping("/blockUser")
	public ModelAndView blockUser(HttpServletRequest request, HttpServletResponse response,@RequestParam("userId") Long userId,@RequestParam("status") String status)
	{
		System.out.println(status);
		ModelAndView mv = new ModelAndView();
		if("active".equals(status))
		{

			List<ServiceInstance> instances = discoveryClient.getInstances("ADMINSERVICE");
			ServiceInstance serviceInstance = instances.get(0);

			String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080
			baseUrl = baseUrl + "/admin/blockUser/"+userId;
			
			RestTemplate restTemplate = new RestTemplate();
	        String blocked = restTemplate.exchange(baseUrl, HttpMethod.PUT, null, String.class).getBody();
			
			if("blocked".equals(blocked))
			{
				//mv.addObject("message","Blocked User");
				mv.setViewName("/admin/users.jsp");
			}
			else
			{
				 //mv.addObject("message", "Failed to block user.");
				 mv.setViewName("/admin/users.jsp"); 
			}
			
		}else {
			//mv.addObject("message","User not active to block");
			mv.setViewName("/admin/users.jsp"); 
		}
		return mv;
	}
	
	@RequestMapping("/activateUser")
	public ModelAndView activateUser(HttpServletRequest request, HttpServletResponse response,@RequestParam("userId") Long userId,@RequestParam("status") String status)
	{
		System.out.println(status);
		ModelAndView mv = new ModelAndView();
		if("blocked".equals(status))
		{

			List<ServiceInstance> instances = discoveryClient.getInstances("ADMINSERVICE");
			ServiceInstance serviceInstance = instances.get(0);

			String baseUrl = serviceInstance.getUri().toString(); // return http://localhost:8080
			baseUrl = baseUrl + "/admin/activateUser/"+userId;
			
			RestTemplate restTemplate = new RestTemplate();
	        String active = restTemplate.exchange(baseUrl, HttpMethod.PUT, null, String.class).getBody();
			
			if("active".equals(active))
			{
				//mv.addObject("message","Activated User");
				mv.setViewName("/admin/users.jsp");
			}
			else
			{
				// mv.addObject("message", "Failed to activate user.");
				 mv.setViewName("/admin/users.jsp"); 
			}
			
		}else {
			//mv.addObject("message","User not blocked to active");
			mv.setViewName("/admin/users.jsp"); 
		}
		return mv;
	}
	

}
