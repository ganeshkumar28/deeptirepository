<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Product Page</title>
    <style>
        .view-products-btn {
            padding: 10px 20px;
            font-size: 16px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .view-products-btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<h1>Welcome to Our Store</h1>

<form action="${pageContext.request.contextPath}/buyer/cart" method="get">
    <button type="submit" class="view-products-btn">View Cart</button>
</form>

<form action="${pageContext.request.contextPath}/buyer/wishlist" method="get">
    <button type="submit" class="view-products-btn">View Fav</button>
</form>

<form action="${pageContext.request.contextPath}/buyer/myreviews" method="get">
    <button type="submit" class="view-products-btn">View Review</button>
</form>

</body>
</html>
