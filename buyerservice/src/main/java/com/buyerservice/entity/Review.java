package com.buyerservice.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long reviewId;
	
	private String reviewText;
	private int rating;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Products product;

	public long getReviewId() {
		return reviewId;
	}

	public void setReviewId(long reviewId) {
		this.reviewId = reviewId;
	}

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}
	
	public Long getProductId() {
        return product != null ? product.getProductId() : null;  // Ensure product is not null
    }

    public String getProductName() {
        return product != null ? product.getProductName() : null;  // Ensure product is not null
    }
	
 // New method to get userId from the user object
    public Long getUserId() {
        return user != null ? user.getUserId() : null;  // Ensure user is not null
    }

    // New method to set userId in the user object
    public void setUserId(Long userId) {
        if (this.user == null) {
            this.user = new User();  // Create a new User if null
        }
        this.user.setUserId(userId);  // Set the userId in the User object
    }
	
	
	
}