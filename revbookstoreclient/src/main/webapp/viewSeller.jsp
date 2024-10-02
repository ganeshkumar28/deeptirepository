<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Sellers</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center mb-4">List of Sellers</h2>

        <!-- Check if the seller list exists -->
        <c:choose>
            <c:when test="${not empty seller_list}">
                <table class="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Address</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Iterate over each seller and display their details -->
                        <c:forEach var="seller" items="${seller_list}">
                            <tr>
                                <td>${seller.name}</td>
                                <td>${seller.email}</td>
                                <td>${seller.phoneNumber}</td>
                                <td>${seller.address}</td>
                                <td>
                                    <!-- Add any relevant actions for each seller -->
                                    <form action="${pageContext.request.contextPath}/admin/removeSeller" method="post">
                                        <input type="hidden" name="sellerId" value="${seller.userId}">
                                        <button type="submit" class="btn btn-danger">Remove</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="alert alert-warning text-center" role="alert">
                    No sellers found.
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
