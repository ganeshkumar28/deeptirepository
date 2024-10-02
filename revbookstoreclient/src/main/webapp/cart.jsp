<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Your Cart"/>
</jsp:include>

<%-- Define a total price variable --%>
<% double totalPrice = 0.0; %>

<!-- Main Content -->
<div class="container mt-5">
    <h1 class="mb-4">Shopping Cart</h1>

    <c:choose>
        <c:when test="${not empty products}">
            <!-- Cart Items Table -->
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th scope="col">Cart ID</th>
                        <th scope="col">Product</th>
                        <th scope="col">Description</th>
                        <th scope="col">Quantity</th>
                        <th scope="col">Price</th>
                        <th scope="col">Total</th>
                        <th scope="col">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${products}">
                        <%-- Calculate item total --%>
                        <c:set var="itemTotal" value="${item.price * item.quantity}" />
                        <%-- Update total price --%>
                        <%
                        totalPrice += (Double) pageContext.getAttribute("itemTotal");
                        %>
                        <tr>
                        	
                            <td>${item.cartId}</td>
                            <td>${item.productName}</td>
                            <td>${item.productDescription}</td>
                            
                            <td>
                                <form action="${pageContext.request.contextPath}/buyer/updateQuantity" method="post" class="d-flex">
                                    <input type="hidden" name="cartId" value="${item.cartId}">
                                    <input type="hidden" name="action" value="update">
                                    <input type="number" name="quantity" class="form-control me-2 quantity-input" value="${item.quantity}" min="1">
                                    <button type="submit" class="btn btn-primary">Update</button>
                                </form>
                            </td>
                            <td>&#8377; ${item.price}</td>
                            <td>&#8377; ${itemTotal}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/buyer/removeProductFromCart" method="post">
                                    <input type="hidden" name="cartId" value="${item.cartId}">
                                    <input type="hidden" name="action" value="delete">
                                    <button type="submit" class="btn btn-danger">Remove</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Total Price -->
            <div class="text-end">
                <h4>Total Price: &#8377; <%= totalPrice %></h4>
                <a href="${pageContext.request.contextPath}/checkout.jsp" class="btn btn-success mt-3">Proceed to Checkout</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-warning text-center" role="alert">
                Your cart is empty.
            </div>
            <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">Continue Shopping</a>
        </c:otherwise>
    </c:choose>
</div>

<!-- Inline CSS for quantity input field -->
<style>
    .quantity-input {
        width: 100px; /* Adjust width as needed */
    }
</style>

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
