<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ page import="util.SessionUtil" %>
<%@ page import="model.Complaint" %>
<%
  User currentUser = SessionUtil.getCurrentUser(request);
  if (currentUser == null) {
    response.sendRedirect("login.jsp");
    return;
  }

  Complaint complaint = (Complaint) request.getAttribute("complaint");
  boolean isEdit = complaint != null;
  boolean isAdmin = "ADMIN".equals(currentUser.getRole());
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>CMS - <%= isEdit ? "Edit" : "New" %> Complaint</title>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
  <style>
    .form-header {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      color: white;
      border-radius: 15px 15px 0 0;
    }
    .form-card {
      border-radius: 15px;
      box-shadow: 0 10px 30px rgba(0,0,0,0.1);
    }
    .status-badge {
      font-size: 0.9em;
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
    <div class="navbar-nav ms-auto">
                <span class="navbar-text me-3">
                    <i class="fas fa-user-circle me-1"></i>
                    <%= currentUser.getUsername() %>
                    <span class="badge bg-secondary ms-1"><%= currentUser.getRole() %></span>
                </span>
      <form action="auth" method="post" class="d-inline">
        <input type="hidden" name="action" value="logout">
        <button type="submit" class="btn btn-outline-light btn-sm">
          <i class="fas fa-sign-out-alt me-1"></i>Logout
        </button>
      </form>
    </div>
  </div>
</nav>

<div class="container my-5">
  <!-- Breadcrumb -->
  <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
      <li class="breadcrumb-item"><a href="dashboard.jsp">Dashboard</a></li>
      <li class="breadcrumb-item"><a href="complaint?action=list">Complaints</a></li>
      <li class="breadcrumb-item active"><%= isEdit ? "Edit" : "New" %> Complaint</li>
    </ol>
  </nav>

  <div class="row justify-content-center">
    <div class="col-lg-8">
      <div class="card form-card border-0">
        <div class="card-header form-header text-center py-4">
          <h3 class="mb-2">
            <i class="fas <%= isEdit ? "fa-edit" : "fa-plus-circle" %> me-2"></i>
            <%= isEdit ? "Edit Complaint" : "Submit New Complaint" %>
          </h3>
          <% if (isEdit) { %>
          <p class="mb-0 opacity-75">Complaint ID: #<%= complaint.getId() %></p>
          <% } %>
        </div>

        <div class="card-body p-4">
          <!-- Display current status for edit mode -->
            <% if (isEdit) { %>
          <div class="alert alert-info d-flex align-items-center mb-4">
            <i class="fas fa-info-circle me-3 fa-lg"></i>
            <div>
              <strong>Current Status:</strong>
<%--              <span class="badge bg-<%= getStatusColor(complaint.getStatus()) %> ms-2 status-badge">--%>
<%--                                    <%= complaint.getStatus() %>--%>
<%--                                </span>--%>
              <br>
              <small class="text-muted">
                Created: <%= new java.text.SimpleDateFormat("MMM dd, yyyy 'at' HH:mm").format(complaint.getCreatedAt()) %>
                <% if (complaint.getUpdatedAt() != null && !complaint.getUpdatedAt().equals(complaint.getCreatedAt())) { %>
                | Updated: <%= new java.text.SimpleDateFormat("MMM dd, yyyy 'at' HH:mm").format(complaint.getUpdatedAt()) %>
                <% } %>
              </small>
            </div>
          </div>
            <% } %>

          <!-- Error/Success Messages -->
            <% if(request.getAttribute("error") != null) { %>
          <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="fas fa-exclamation-triangle me-2"></i>
            <%= request.getAttribute("error") %>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
          </div>
            <% } %>

            <% if(request.getParameter("message") != null) { %>
          <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="fas fa-check-circle me-2"></i>
            <%= request.getParameter("message") %>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
          </div>
            <% } %>

          <form action="complaint" method="post" class="needs-validation" novalidate>
            <input type="hidden" name="action" value="<%= isEdit ? "update" : "create" %>">
              <% if(isEdit) { %>
            <input type="hidden" name="id" value="<%= complaint.getId() %>">
              <% } %>

            <!-- Employee Section -->
              <% if (!isAdmin || (isAdmin && !isEdit)) { %>
            <div class="row mb-4">
              <div class="col-12">
                <h5 class="text-primary border-bottom pb-2 mb-3">
                  <i class="fas fa-user me-2"></i>Complaint Details
                </h5>
              </div>

              <div class="col-md-12 mb-3">
                <label for="title" class="form-label">
                  <i class="fas fa-heading me-1"></i>Title <span class="text-danger">*</span>
                </label>
                <input type="text" class="form-control form-control-lg"
                       id="title" name="title"
                       value="<%= isEdit ? complaint.getTitle() : "" %>"
                       placeholder="Brief description of the issue"
                       required <%= (isEdit && "RESOLVED".equals(complaint.getStatus()) && !isAdmin) ? "readonly" : "" %>>
                <div class="invalid-feedback">
                  Please provide a complaint title.
                </div>
              </div>

              <div class="col-md-12 mb-3">
                <label for="description" class="form-label">
                  <i class="fas fa-align-left me-1"></i>Description <span class="text-danger">*</span>
                </label>
                <textarea class="form-control" id="description" name="description"
                          rows="5" placeholder="Detailed description of the complaint..."
                          required <%= (isEdit && "RESOLVED".equals(complaint.getStatus()) && !isAdmin) ? "readonly" : "" %>><%= isEdit ? complaint.getDescription() : "" %></textarea>
                <div class="invalid-feedback">
                  Please provide a detailed description.
                </div>
              </div>

              <div class="col-md-6 mb-3">
                <label for="priority" class="form-label">
                  <i class="fas fa-exclamation-circle me-1"></i>Priority Level
                </label>
                <select class="form-select form-select-lg" id="priority" name="priority"
                        <%= (isEdit && "RESOLVED".equals(complaint.getStatus()) && !isAdmin) ? "disabled" : "" %>>
                  <option value="LOW" <%= isEdit && "LOW".equals(complaint.getPriority()) ? "selected" : "" %>>
                    🟢 Low Priority
                  </option>
                  <option value="MEDIUM" <%= isEdit && "MEDIUM".equals(complaint.getPriority()) ? "selected" : (!isEdit ? "selected" : "") %>>
                    🟡 Medium Priority
                  </option>
                  <option value="HIGH" <%= isEdit && "HIGH".equals(complaint.getPriority()) ? "selected" : "" %>>
                    🔴 High Priority
                  </option>
                </select>
              </div>
            </div>
              <% } %>

            <!-- Admin Section -->
              <% if (isAdmin && isEdit) { %>
            <div class="row mb-4">
              <div class="col-12">
                <h5 class="text-success border-bottom pb-2 mb-3">
                  <i class="fas fa-user-cog me-2"></i>Admin Actions
                </h5>
              </div>

              <div class="col-md-6 mb-3">
                <label for="status" class="form-label">
                  <i class="fas fa-tasks me-1"></i>Status
                </label>
                <select class="form-select form-select-lg" id="status" name="status">
                  <option value="PENDING" <%= "PENDING".equals(complaint.getStatus()) ? "selected" : "" %>>
                    🟠 Pending Review
                  </option>
                  <option value="IN_PROGRESS" <%= "IN_PROGRESS".equals(complaint.getStatus()) ? "selected" : "" %>>
                    🔵 In Progress
                  </option>
                  <option value="RESOLVED" <%= "RESOLVED".equals(complaint.getStatus()) ? "selected" : "" %>>
                    🟢 Resolved
                  </option>
                  <option value="CLOSED" <%= "CLOSED".equals(complaint.getStatus()) ? "selected" : "" %>>
                    ⚫ Closed
                  </option>
                </select>
              </div>

              <div class="col-md-12 mb-3">
                <label for="adminRemarks" class="form-label">
                  <i class="fas fa-comment-alt me-1"></i>Admin Remarks
                </label>
                <textarea class="form-control" id="adminRemarks" name="adminRemarks"
                          rows="3" placeholder="Add administrative notes or resolution details..."><%= isEdit && complaint.getAdminRemarks() != null ? complaint.getAdminRemarks() : "" %></textarea>
                <div class="form-text">
                  <i class="fas fa-info-circle me-1"></i>
                  These remarks will be visible to the employee who submitted the complaint.
                </div>
              </div>
            </div>

            <!-- Display complaint details for admin -->
            <div class="row mb-4">
              <div class="col-12">
                <h5 class="text-info border-bottom pb-2 mb-3">
                  <i class="fas fa-file-alt me-2"></i>Complaint Information
                </h5>
              </div>
              <div class="col-md-12">
                <div class="card bg-light">
                  <div class="card-body">
                    <p><strong>Title:</strong> <%= complaint.getTitle() %></p>
                    <p><strong>Description:</strong> <%= complaint.getDescription() %></p>
                    <p><strong>Submitted By:</strong> <%= complaint.getEmployeeId() %></p>
                    <p><strong>Priority:</strong> <%= complaint.getPriority() %></p>
                    <p><strong>Status:</strong> <%= complaint.getStatus() %></p>
                    <p><strong>Created At:</strong> <%= new java.text.SimpleDateFormat("MMM dd, yyyy HH:mm").format(complaint.getCreatedAt()) %></p>
                    <% if (complaint.getUpdatedAt() != null) { %>
                    <p><strong>Updated At:</strong> <%= new java.text.SimpleDateFormat("MMM dd, yyyy HH:mm").format(complaint.getUpdatedAt()) %></p>
                    <% } %>
                  </div>
                </div>
              </div>
            </div>
            <% } %>

            <div class="d-flex justify-content-end">
              <a href="complaint?action=list" class="btn btn-secondary me-2">
                <i class="fas fa-arrow-left me-1"></i>Back
              </a>
              <button type="submit" class="btn btn-primary">
                <i class="fas fa-save me-1"></i><%= isEdit ? "Update" : "Submit" %> Complaint
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- Bootstrap JS Bundle -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>

<!-- Optional: Form validation -->
<script>
  (() => {
    'use strict';
    const forms = document.querySelectorAll('.needs-validation');
    Array.from(forms).forEach(form => {
      form.addEventListener('submit', event => {
        if (!form.checkValidity()) {
          event.preventDefault();
          event.stopPropagation();
        }
        form.classList.add('was-validated');
      }, false);
    });
  })();
</script>
</body>
</html>
