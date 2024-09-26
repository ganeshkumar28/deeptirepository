<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <style>
        /* Background for the page */
        body {
            background: linear-gradient(135deg, #ece9e6, #ffffff); /* Subtle gradient background */
            font-family: 'Poppins', sans-serif; /* Modern font */
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 1200px;
            margin: auto;
            padding: 2rem;
        }

        h1, h2, h3, h4, h5 {
            color: #333; /* Darker heading color */
        }

        /* Card styling */
        .card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 10px 15px rgba(0, 0, 0, 0.1);
            overflow: hidden;
            background: linear-gradient(145deg, #f0f0f0, #ffffff);
            transition: transform 0.4s, box-shadow 0.4s;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 20px 30px rgba(0, 0, 0, 0.2);
        }

        .card-body {
            padding: 2rem;
        }

        .img-fluid {
            border-radius: 15px;
            transition: transform 0.3s, opacity 0.3s;
        }

        .img-fluid:hover {
            transform: scale(1.05);
            opacity: 0.9;
        }

        /* Button styling */
        .btn {
            border-radius: 30px;
            padding: 0.75rem 1.5rem;
            font-size: 1rem;
            transition: background 0.4s, transform 0.3s;
        }

        .btn-primary {
            background: linear-gradient(45deg, #007bff, #0056b3);
            border: none;
            color: #fff;
        }

        .btn-primary:hover {
            background: linear-gradient(45deg, #0056b3, #004085);
            transform: scale(1.05);
        }

        .btn-success {
            background: linear-gradient(45deg, #28a745, #218838);
            border: none;
            color: #fff;
        }

        .btn-success:hover {
            background: linear-gradient(45deg, #218838, #1e7e34);
            transform: scale(1.05);
        }

        .alert {
            border-radius: 15px;
            padding: 1.5rem;
            font-size: 1.1rem;
        }

        /* Form styling */
        .form-control {
            border-radius: 10px;
            padding: 0.75rem;
            border: 1px solid #ccc;
            transition: border-color 0.3s;
        }

        .form-control:focus {
            border-color: #007bff;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
        }

        /* Checkbox styling */
        .form-check-input {
            margin-top: 0.3rem;
            margin-left: 0;
        }

        .form-check-label {
            margin-left: 0.5rem;
            font-size: 1rem;
            color: #333;
        }

        /* List group for reviews */
        .list-group-item {
            border-radius: 10px;
            background: #f8f9fa;
            border: 1px solid #e9ecef;
            margin-bottom: 0.5rem;
        }
    </style>
</head>
<body>

<jsp:include page="header.jsp">
	<jsp:param name="pageTitle" value="Product Details" />
</jsp:include>

<!-- Main Content -->
<div class="container mt-5">
	<h1 class="mb-4">Book Details</h1>

	<div class="row">
		<!-- Product Image Section (Left Side) -->
		<div class="col-md-4">
			<c:choose>
				<c:when test="${not empty product.imageUrl}">
					<img src="${product.imageUrl}" alt="${product.name}"
						class="img-fluid mb-4">
				</c:when>
				<c:otherwise>
					<img src="https://via.placeholder.com/400" alt="Placeholder Image"
						class="img-fluid mb-4">
				</c:otherwise>
			</c:choose>
		</div>

		<!-- Product Details and Reviews Section (Right Side) -->
		<div class="col-md-8">
			<c:choose>
				<c:when test="${not empty product}">
					<div class="card mb-4">
						<div class="card-body">
							<h2 class="card-title">${product.name}</h2>
							<p class="card-text">${product.description}</p>
							<p class="card-text">
								<strong>Category:</strong> ${product.category}
							</p>
							<p class="card-text">
								<strong>Price:</strong> &#8377;${product.price}
							</p>

							<c:choose>
								<c:when test="${incart}">
									<a href="${pageContext.request.contextPath}/cart"
										class="btn btn-primary">View in Cart</a>
								</c:when>
								<c:otherwise>
									<!-- Add to Cart Button -->
									<form method="post"
										action="${pageContext.request.contextPath}/buyer/addProductsToCarts?id=${product.id}">
										<input type="hidden" name="productId" value="${product.id}">
										<input type="hidden"name="action" value="add">
										<button type="submit" class="btn btn-success mt-3">Add
											to Cart</button>
									</form>
								</c:otherwise>
							</c:choose>

							<!-- Seller Information -->
							<c:if test="${not empty retailer}">
								<div class="mt-4">
									<h4>Seller Information</h4>
									<p>
										<strong>Name:</strong> ${retailer.name}
									</p>
									<p>
										<strong>Address:</strong> ${retailer.address}
									</p>
								</div>
							</c:if>

							<!-- Reviews Section -->
							<h3 class="mb-4 mt-4">Reviews</h3>
							<c:choose>
								<c:when test="${not empty reviews}">
									<ul class="list-group mb-4">
										<c:forEach var="review" items="${reviews}">
											<li class="list-group-item">
												<h5 class="mb-1">${review.username}</h5>
												<p class="mb-1">${review.message}</p> <small>Rating:
													${review.rating} / 5</small>
											</li>
										</c:forEach>
									</ul>
								</c:when>
								<c:otherwise>
									<div class="alert alert-info" role="alert">No reviews
										yet.</div>
								</c:otherwise>
							</c:choose>

							<!-- Review Form -->
							<h5 class="mb-4">Post a Review</h5>
							<c:choose>
								<c:when test="${sessionScope['user-role'] == 'buyer'}">
									<form method="post"
										action="${pageContext.request.contextPath}/buyer/submitreview">
										<input type="hidden" name="productId" value="${product.id}">
										<div class="mb-3">
											<label for="reviewText" class="form-label">Your
												Review</label>
											<textarea class="form-control" id="reviewText"
												name="reviewText" rows="4" required></textarea>
										</div>
										<div class="mb-3">
											<label class="form-label">Rating</label>
											<div class="form-check form-check-inline">
												<input class="form-check-input" type="radio" id="rating1"
													name="rating" value="1" required> <label
													class="form-check-label" for="rating1">1 - Poor</label>
											</div>
											<div class="form-check form-check-inline">
												<input class="form-check-input" type="radio" id="rating2"
													name="rating" value="2"> <label
													class="form-check-label" for="rating2">2 - Fair</label>
											</div>
											<div class="form-check form-check-inline">
												<input class="form-check-input" type="radio" id="rating3"
													name="rating" value="3"> <label
													class="form-check-label" for="rating3">3 - Good</label>
											</div>
											<div class="form-check form-check-inline">
												<input class="form-check-input" type="radio" id="rating4"
													name="rating" value="4"> <label
													class="form-check-label" for="rating4">4 - Very Good</label>
											</div>
											<div class="form-check form-check-inline">
												<input class="form-check-input" type="radio" id="rating5"
													name="rating" value="5"> <label
													class="form-check-label" for="rating5">5 - Excellent</label>
											</div>
										</div>
										<button type="submit" class="btn btn-primary mt-3">Submit
											Review</button>
									</form>
								</c:when>
								<c:otherwise>
									<div class="alert alert-warning" role="alert">You must be
										logged in as a buyer to post a review.</div>
								</c:otherwise>
							</c:choose>

						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-danger" role="alert">Product not found.</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>
</body>
</html>