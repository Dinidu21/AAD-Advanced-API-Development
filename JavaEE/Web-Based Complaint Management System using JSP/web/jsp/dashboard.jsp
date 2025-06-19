<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="" %>
<%@ page import="" %>
<%@ page import="util.SessionUtil" %>
<%@ page import="model.User" %>
<%
    User currentUser = SessionUtil.getCurrentUser(request);
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CMS - Dashboard</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        .hero-section {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 60px 0;
        }
        .dashboard-card {
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            border: none;
            border-radius: 15px;
        }
        .dashboard-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 25px rgba(0,0,0,0.15);
        }
        .stat-card {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            color: white;
        }
        .action-card {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
            color: white;
        }
    </style>
</head>
<body class="bg-light">
<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container">
        <a class="navbar-brand" href="dashboard.jsp">
            <i class="fas fa-clipboard-list me-2"></i>CMS Portal
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="dashboard.jsp">
                        <i class="fas fa-home me-1"></i>Dashboard
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="complaint?action=list">
                        <i class="fas fa-list me-1"></i>My Complaints
                    </a>
                </li>
                <% if ("EMPLOYEE".equals(currentUser.getRole())) { %>
                <li class="nav-item">
                    <a class="nav-link" href="complaint?action=new">
                        <i class="fas fa-plus me-1"></i>New Complaint
                    </a>
                </li>
                <% } %>
            </ul>

            <ul class="navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                        <i class="fas fa-user-circle me-1"></i>
                        <%= currentUser.getUsername() %>
                        <span class="badge bg-secondary ms-1"><%= currentUser.getRole() %></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="#"><i class="fas fa-user me-2"></i>Profile</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li>
                            <form action="auth" method="post" class="d-inline">
                                <input type="hidden" name="action" value="logout">
                                <button type="submit" class="dropdown-item text-danger">
                                    <i class="fas fa-sign-out-alt me-2"></i>Logout
                                </button>
                            </form>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Hero Section -->
<section class="hero-section">
    <div class="container text-center">
        <h1 class="display-4 mb-3">
            <i class="fas fa-chart-line me-3"></i>
            Welcome, <%= currentUser.getUsername() %>!
        </h1>
        <p class="lead mb-4">
            <% if ("ADMIN".equals(currentUser.getRole())) { %>
            Manage and oversee all complaint submissions in the system
            <% } else { %>
            Submit and track your complaints efficiently
            <% } %>
        </p>
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="row text-center">
                    <div class="col-6 col-md-3">
                        <div class="bg-white bg-opacity-25 rounded p-3">
                            <i class="fas fa-calendar-day fa-2x mb-2"></i>
                            <h6>Today</h6>
                            <p class="mb-0"><%= new java.text.SimpleDateFormat("MMM dd, yyyy").format(new java.util.Date()) %></p>
                        </div>
                    </div>
                    <div class="col-6 col-md-3">
                        <div class="bg-white bg-opacity-25 rounded p-3">
                            <i class="fas fa-user-shield fa-2x mb-2"></i>
                            <h6>Role</h6>
                            <p class="mb-0"><%= currentUser.getRole() %></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- Dashboard Cards -->
<div class="container my-5">
    <div class="row g-4">
        <% if ("EMPLOYEE".equals(currentUser.getRole())) { %>
        <!-- Employee Dashboard -->
        <div class="col-md-6">
            <div class="card dashboard-card stat-card h-100">
                <div class="card-body text-center p-4">
                    <i class="fas fa-plus-circle fa-4x mb-3 opacity-75"></i>
                    <h4 class="card-title">Submit New Complaint</h4>
                    <p class="card-text">Report issues, problems, or suggestions to the administration.</p>
                    <a href="complaint?action=new" class="btn btn-light btn-lg">
                        <i class="fas fa-plus me-2"></i>Create Complaint
                    </a>
                </div>
            </div>
        </div>

        <div class="col-md-6">
            <div class="card dashboard-card action-card h-100">
                <div class="card-body text-center p-4">
                    <i class="fas fa-list-alt fa-4x mb-3 opacity-75"></i>
                    <h4 class="card-title">My Complaints</h4>
                    <p class="card-text">View, edit, and track the status of your submitted complaints.</p>
                    <a href="complaint?action=list" class="btn btn-light btn-lg">
                        <i class="fas fa-eye me-2"></i>View Complaints
                    </a>
                </div>
            </div>
        </div>
        <% } else { %>
        <!-- Admin Dashboard -->
        <div class="col-md-6">
            <div class="card dashboard-card stat-card h-100">
                <div class="card-body text-center p-4">
                    <i class="fas fa-tasks fa-4x mb-3 opacity-75"></i>
                    <h4 class="card-title">Manage All Complaints</h4>
                    <p class="card-text">Review, update status, and add remarks to all complaints in the system.</p>
                    <a href="complaint?action=list" class="btn btn-light btn-lg">
                        <i class="fas fa-cogs me-2"></i>Manage Complaints
                    </a>
                </div>
            </div>
        </div>

        <div class="col-md-6">
            <div class="card dashboard-card action-card h-100">
                <div class="card-body text-center p-4">
                    <i class="fas fa-chart-bar fa-4x mb-3 opacity-75"></i>
                    <h4 class="card-title">System Reports</h4>
                    <p class="card-text">Generate reports and analytics on complaint trends and resolution.</p>
                    <button class="btn btn-light btn-lg" onclick="alert('Reports feature coming soon!')">
                        <i class="fas fa-chart-line me-2"></i>View Reports
                    </button>
                </div>
            </div>
        </div>
        <% } %>
    </div>

    <!-- Quick Actions -->
    <div class="row mt-5">
        <div class="col-12">
            <div class="card">
                <div class="card-header bg-primary text-white">
                    <h5 class="mb-0"><i class="fas fa-bolt me-2"></i>Quick Actions</h5>
                </div>
                <div class="card-body">
                    <div class="row text-center">
                        <div class="col-6 col-md-3 mb-3">
                            <a href="complaint?action=list" class="btn btn-outline-primary btn-sm w-100">
                                <i class="fas fa-list d-block mb-1"></i>
                                <small>View All</small>
                            </a>
                        </div>
                        <% if ("EMPLOYEE".equals(currentUser.getRole())) { %>
                        <div class="col-6 col-md-3 mb-3">
                            <a href="complaint?action=new" class="btn btn-outline-success btn-sm w-100">
                                <i class="fas fa-plus d-block mb-1"></i>
                                <small>New Complaint</small>
                            </a>
                        </div>
                        <% } %>
                        <div class="col-6 col-md-3 mb-3">
                            <button class="btn btn-outline-info btn-sm w-100" onclick="window.print()">
                                <i class="fas fa-print d-block mb-1"></i>
                                <small>Print</small>
                            </button>
                        </div>
                        <div class="col-6 col-md-3 mb-3">
                            <button class="btn btn-outline-secondary btn-sm w-100" onclick="location.reload()">
                                <i class="fas fa-sync d-block mb-1"></i>
                                <small>Refresh</small>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Footer -->
<footer class="bg-dark text-white mt-5 py-4">
    <div class="container text-center">
        <div class="row">
            <div class="col-md-6">
                <p class="mb-0">
                    <i class="fas fa-building me-2"></i>
                    Municipal IT Division - Complaint Management System
                </p>
            </div>
            <div class="col-md-6">
                <p class="mb-0">
                    <i class="fas fa-envelope me-2"></i>
                    support@municipal.gov |
                    <i class="fas fa-phone ms-2 me-1"></i>
                    +1 (555) 123-4567
                </p>
            </div>
        </div>
    </div>
</footer>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>