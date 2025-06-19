package controller;


import dao.ComplaintDAO;
import dao.ComplaintDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Complaint;
import model.User;
import util.SessionUtil;


import java.io.IOException;
import java.util.List;

@WebServlet("/complaint")
public class ComplaintController extends HttpServlet {

    private ComplaintDAO complaintDAO;

    @Override
    public void init() throws ServletException {
        complaintDAO = new ComplaintDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is logged in
        if (!SessionUtil.isLoggedIn(request)) {
            response.sendRedirect("../jsp/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "list":
                    showComplaintList(request, response);
                    break;
                case "new":
                    showNewComplaintForm(request, response);
                    break;
                case "edit":
                    showEditComplaintForm(request, response);
                    break;
                case "view":
//                    showComplaintDetails(request, response);
                    break;
                default:
                    showComplaintList(request, response);
            }
        } catch (Exception e) {
            handleError(request, response, "Error processing request: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is logged in
        if (!SessionUtil.isLoggedIn(request)) {
            response.sendRedirect("../jsp/login.jsp");
            return;
        }

        String action = request.getParameter("action");

        try {
            switch (action) {
                case "create":
                    createComplaint(request, response);
                    break;
                case "update":
                    updateComplaint(request, response);
                    break;
                case "delete":
                    deleteComplaint(request, response);
                    break;
                default:
                    response.sendRedirect("complaint?action=list");
            }
        } catch (Exception e) {
            handleError(request, response, "Error processing request: " + e.getMessage());
        }
    }

    private void showComplaintList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User currentUser = SessionUtil.getCurrentUser(request);
        List<Complaint> complaints;

        if ("ADMIN".equals(currentUser.getRole())) {
            // Admin can see all complaints
            complaints = complaintDAO.getAllComplaints();
        } else {
            // Employee can see only their complaints
            complaints = complaintDAO.getComplaintsByEmployee(currentUser.getId());
        }

        request.setAttribute("complaints", complaints);
        request.setAttribute("currentUser", currentUser);
        request.getRequestDispatcher("../jsp/complaint-list.jsp").forward(request, response);
    }

    private void showNewComplaintForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User currentUser = SessionUtil.getCurrentUser(request);

        // Only employees can create new complaints
        if (!"EMPLOYEE".equals(currentUser.getRole())) {
            handleError(request, response, "Access denied. Only employees can create complaints.");
            return;
        }

        request.getRequestDispatcher("../jsp/complaint-form.jsp").forward(request, response);
    }

    private void showEditComplaintForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int complaintId = Integer.parseInt(request.getParameter("id"));
        Complaint complaint = complaintDAO.getComplaintById(complaintId);

        if (complaint == null) {
            handleError(request, response, "Complaint not found.");
            return;
        }

        User currentUser = SessionUtil.getCurrentUser(request);

        // Check if user has permission to edit this complaint
        if ("EMPLOYEE".equals(currentUser.getRole()) &&
                complaint.getEmployeeId() != currentUser.getId()) {
            handleError(request, response, "Access denied. You can only edit your own complaints.");
            return;
        }

        // Employees can't edit resolved complaints
        if ("EMPLOYEE".equals(currentUser.getRole()) &&
                "RESOLVED".equals(complaint.getStatus())) {
            handleError(request, response, "Cannot edit resolved complaints.");
            return;
        }

        request.setAttribute("complaint", complaint);
        request.getRequestDispatcher("../jsp/complaint-form.jsp").forward(request, response);
    }

    private void createComplaint(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User currentUser = SessionUtil.getCurrentUser(request);

        // Only employees can create complaints
        if (!"EMPLOYEE".equals(currentUser.getRole())) {
            handleError(request, response, "Access denied.");
            return;
        }

        // Get form parameters
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String priority = request.getParameter("priority");

        // Validate input
        if (title == null || title.trim().isEmpty() ||
                description == null || description.trim().isEmpty()) {
            request.setAttribute("error", "Title and description are required.");
            request.getRequestDispatcher("../jsp/complaint-form.jsp").forward(request, response);
            return;
        }

        // Create complaint object
        Complaint complaint = new Complaint();
        complaint.setTitle(title.trim());
        complaint.setDescription(description.trim());
        complaint.setPriority(priority != null ? priority : "MEDIUM");
        complaint.setEmployeeId(currentUser.getId());
        complaint.setStatus("PENDING");

        // Save to database
        boolean success = complaintDAO.createComplaint(complaint);

        if (success) {
            response.sendRedirect("complaint?action=list&message=Complaint submitted successfully");
        } else {
            request.setAttribute("error", "Failed to submit complaint. Please try again.");
            request.getRequestDispatcher("../jsp/complaint-form.jsp").forward(request, response);
        }
    }

    private void updateComplaint(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int complaintId = Integer.parseInt(request.getParameter("id"));
        Complaint existingComplaint = complaintDAO.getComplaintById(complaintId);

        if (existingComplaint == null) {
            handleError(request, response, "Complaint not found.");
            return;
        }

        User currentUser = SessionUtil.getCurrentUser(request);

        // Check permissions
        if ("EMPLOYEE".equals(currentUser.getRole())) {
            if (existingComplaint.getEmployeeId() != currentUser.getId()) {
                handleError(request, response, "Access denied.");
                return;
            }
            if ("RESOLVED".equals(existingComplaint.getStatus())) {
                handleError(request, response, "Cannot edit resolved complaints.");
                return;
            }

            // Employee can only update title, description, and priority
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String priority = request.getParameter("priority");

            existingComplaint.setTitle(title.trim());
            existingComplaint.setDescription(description.trim());
            existingComplaint.setPriority(priority != null ? priority : "MEDIUM");

        } else if ("ADMIN".equals(currentUser.getRole())) {
            // Admin can update status and add remarks
            String status = request.getParameter("status");
            String adminRemarks = request.getParameter("adminRemarks");

            if (status != null) {
                existingComplaint.setStatus(status);
            }
            if (adminRemarks != null) {
                existingComplaint.setAdminRemarks(adminRemarks.trim());
            }
        }

        // Update in database
        boolean success = complaintDAO.updateComplaint(existingComplaint);

        if (success) {
            response.sendRedirect("complaint?action=list&message=Complaint updated successfully");
        } else {
            request.setAttribute("error", "Failed to update complaint. Please try again.");
            request.setAttribute("complaint", existingComplaint);
            request.getRequestDispatcher("../jsp/complaint-form.jsp").forward(request, response);
        }
    }

    private void deleteComplaint(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int complaintId = Integer.parseInt(request.getParameter("id"));
        Complaint complaint = complaintDAO.getComplaintById(complaintId);

        if (complaint == null) {
            handleError(request, response, "Complaint not found.");
            return;
        }

        User currentUser = SessionUtil.getCurrentUser(request);

        // Check permissions
        if ("EMPLOYEE".equals(currentUser.getRole())) {
            if (complaint.getEmployeeId() != currentUser.getId()) {
                handleError(request, response, "Access denied.");
                return;
            }
            if ("RESOLVED".equals(complaint.getStatus())) {
                handleError(request, response, "Cannot delete resolved complaints.");
                return;
            }
        }
        // Admins can delete any complaint

        boolean success = complaintDAO.deleteComplaint(complaintId);

        if (success) {
            response.sendRedirect("complaint?action=list&message=Complaint deleted successfully");
        } else {
            handleError(request, response, "Failed to delete complaint. Please try again.");
        }
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        request.setAttribute("error", errorMessage);
        request.getRequestDispatcher("../jsp/error.jsp").forward(request, response);
    }
}
