<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <style>
        /* Background for the page */
        body {
            background: linear-gradient(135deg, #ece9e6, #ffffff); /* Subtle gradient background */
            font-family: 'Poppins', sans-serif; /* Modern font */
        }

        /* Dynamic Card styling */
        .card {
            border: none;
            border-radius: 20px;
            overflow: hidden;
            transition: transform 0.4s, box-shadow 0.4s;
            background: linear-gradient(145deg, #f0f0f0, #ffffff); /* Soft gradient for card background */
            box-shadow: 0 10px 15px rgba(0, 0, 0, 0.1); /* Soft shadow effect */
            height: 100%;
            display: flex;
            flex-direction:column;
        }

        /* Hover effect for cards */
        .card:hover {
            transform: translateY(-10px) scale(1.02); /* Slight lift and scale effect */
            box-shadow: 0 20px 30px rgba(0, 0, 0, 0.2); /* Enhanced shadow on hover */
        }

        .card-img-top {
            border-bottom: 1px solid #ddd;
            transition: transform 0.3s, opacity 0.3s;
            border-radius: 20px 20px 0 0; /* Rounded top corners */
        }

        .card-img-top:hover {
            transform: scale(1.05); /* Slight zoom on hover */
            opacity: 0.9;
        }

        .card-title {
            font-size: 1.5rem;
            font-weight: 700;
            margin-bottom: 0.75rem;
            color: #333; /* Darker title color for better readability */
        }

        .card-text {
            color: #666; /* Muted text color */
            margin-bottom: 1rem;
        }

        .text-truncate {
            display: -webkit-box;
            -webkit-line-clamp: 3;
            -webkit-box-orient: vertical;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        /* Dynamic button styling */
        .btn-info {
            background: linear-gradient(45deg, #6c757d, #343a40); /* Gradient button background */
            border: none;
            border-radius: 30px;
            padding: 0.75rem 1.5rem;
            font-size: 1rem;
            color: #ffffff;
            transition: background 0.4s, transform 0.3s;
        }
                 
         .btn-grad {
            background-image: linear-gradient(to right, #FF512F 0%, #F09819  51%, #FF512F  100%);
            margin: 10px;
            padding: 15px 45px;
            text-align: center;
            font-size:18px;
            text-transform: uppercase;
            transition: 0.5s;
            background-size: 200% auto;
            color: white;            
            box-shadow: 0 0 20px #eee;
            border-radius: 10px;
            display: block;
          }

          .btn-grad:hover {
            background-position: right center; /* change the direction of the change here */
            color: #fff;
            text-decoration: none;
          }
         

        .btn-info:hover {
            background: linear-gradient(45deg, #343a40, #000000); /* Darker gradient on hover */
            transform: scale(1.05); /* Slightly enlarge on hover */
        }
        
                 
         .btn-grad1 {
            background-image: linear-gradient(to right, #1D976C 0%, #93F9B9  51%, #1D976C  100%);
            margin: 10px;
            padding: 15px 45px;
            text-align: center;
            text-transform: uppercase;
            transition: 0.5s;
            background-size: 200% auto;
            color: white;            
            box-shadow: 0 0 20px #eee;
            border-radius: 10px;
            display: block;
          }

          .btn-grad1:hover {
            background-position: right center; /* change the direction of the change here */
            color: #fff;
            text-decoration: none;
          }
         
    </style>
    <script>
        function showFavoriteAlert(event) {
            event.preventDefault(); // Prevent the default link behavior
            alert("Product added to favorites!");
            // Optionally redirect to the URL if needed
            window.location.href = event.currentTarget.href;
        }
    </script>
</head>
<body>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Product Details" />
</jsp:include>

<!-- Main Content -->
<div class="container mt-5">
    <h1 class="mb-4">Books List</h1>
    
    <!-- Category Dropdown -->
    <div class="mb-4">
        <form action="${pageContext.request.contextPath}/products" method="get">
            <div class="input-group">
                <select class="form-select" name="category" onchange="this.form.submit()">
                    <option value="">Select Category</option>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category}" <c:if test="${category == param.category}">selected</c:if>>${category}</option>
                    </c:forEach>
                </select>
            </div>
        </form>
    </div>

    <div class="row row-cols-2 row-cols-md-4 g-4">
        <c:forEach var="product" items="${product_list}">
            <div class="col">
                <div class="card">
                    <img src="${product.imageUrl}" class="card-img-top" alt="${product.name}">
                    <div class="card-body">
                        <h5 class="card-title">${product.name}</h5>
                        <p class="card-text text-truncate">${product.description}</p>
                        <p class="card-text">
                            <strong>MRP Price:</stron8g> &#8377;${product.price}</strong>
                            <br>
                            <strong style="color: Red">Discount Price:</strong> <strong style="font-weight:500;color:Red;">&#8377;${product.discount_price}</strong>
                        </p>
                        <a style="text-decoration: none;" href="${pageContext.request.contextPath}/buyer/viewProductDetails?id=${product.id}" class="btn-grad1" style="font-weight:900;font-size:18px;">View Details</a>
                        <a style="text-decoration: none;" href="${pageContext.request.contextPath}/buyer/addProductToFavorite?id=${product.id}" class="btn-grad" onclick="showFavoriteAlert(event)" style="font-weight:900;font-size:18px;">Favorite</a>
                    </div>
                </div>
            </div>
        </c:forEach>
        <c:if test="${empty products}">
            <div class="col">
                <div class="alert alert-warning text-center" role="alert">No products found.</div>
            </div>
        </c:if>
    </div>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>