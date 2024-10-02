<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>RevStore Admin</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- FontAwesome Icons -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f6f9;
        }
        .wrapper {
            display: flex;
            width: 100%;
            height: 100vh;
        }
        .sidebar {
            width: 250px;
            background-color: #343a40;
            color: white;
            height: 100%;
            padding-top: 20px;
        }
        .sidebar ul {
            list-style-type: none;
            padding: 0;
        }
        .sidebar ul li {
            padding: 15px 20px;
            cursor: pointer;
        }
        .sidebar ul li:hover {
            background-color: #495057;
        }
        .sidebar ul li a {
            color: white;
            text-decoration: none;
        }
        .main-content {
            flex-grow: 1;
            padding: 20px;
        }
        .card {
            margin-bottom: 20px;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .header h1 {
            font-size: 24px;
            color: #343a40;
        }
    </style>
</head>
<body>
    <div class="wrapper">
        <!-- Sidebar -->
        <div class="sidebar">
            <h3 class="text-center">RevStore Admin</h3>
            <ul>
                <li><a href="viewBuyers.jsp"><i class="fas fa-users"></i> View Buyers</a></li>
                <li><a href="viewSellers.jsp"><i class="fas fa-store"></i> View Sellers</a></li>
                <li><a href="viewComplaints.jsp"><i class="fas fa-exclamation-circle"></i> View Complaints</a></li>
                <li><a href="viewOrders.jsp"><i class="fas fa-box"></i> View Orders</a></li>
            </ul>
        </div>

        <!-- Main Content -->
        <div class="main-content">
            <div class="header">
                <h1>Dashboard Overview</h1>
                <div>
                    <button class="btn btn-primary">Logout</button>
                </div>
            </div>

            <!-- Cards Section -->
            <div class="row">
                <!-- Buyers Card -->
                <div class="col-md-3">
                    <div class="card text-white bg-info">
                        <div class="card-body">
                            <h5 class="card-title">Total Buyers</h5>
                            <p class="card-text">123</p>
                            <a href="viewBuyers.jsp" class="btn btn-light">View Buyers</a>
                        </div>
                    </div>
                </div>

                <!-- Sellers Card -->
                <div class="col-md-3">
                    <div class="card text-white bg-success">
                        <div class="card-body">
                            <h5 class="card-title">Total Sellers</h5>
                            <p class="card-text">45</p>
                            <a href="viewSellers.jsp" class="btn btn-light">View Sellers</a>
                        </div>
                    </div>
                </div>

                <!-- Complaints Card -->
                <div class="col-md-3">
                    <div class="card text-white bg-warning">
                        <div class="card-body">
                            <h5 class="card-title">Total Complaints</h5>
                            <p class="card-text">10</p>
                            <a href="viewComplaints.jsp" class="btn btn-light">View Complaints</a>
                        </div>
                    </div>
                </div>

                <!-- Orders Card -->
                <div class="col-md-3">
                    <div class="card text-white bg-danger">
                        <div class="card-body">
                            <h5 class="card-title">Total Orders</h5>
                            <p class="card-text">230</p>
                            <a href="viewOrders.jsp" class="btn btn-light">View Orders</a>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Additional content can go here -->
        </div>
    </div>

    <!-- Bootstrap JS and jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
</body>
</html>
 