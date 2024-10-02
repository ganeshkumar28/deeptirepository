<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin - View Sellers</title>
</head>
<body>
    <h2>Welcome, Admin</h2>
    
    <form action="/revbookstoreclient/admin/viewSellers" method="GET">
        <button type="submit">View Sellers</button>
    </form>
    <form action="/revbookstoreclient/admin/viewBuyers" method="GET">
        <button type="submit">View Buyers</button>
    </form>
    <form action="/revbookstoreclient/admin/viewComplaints" method="GET">
        <button type="submit">View Complaints</button>
    </form>
     <form action="/revbookstoreclient/register" method="GET">
        <button type="submit">Register</button>
    </form>
    
</body>
</html>
