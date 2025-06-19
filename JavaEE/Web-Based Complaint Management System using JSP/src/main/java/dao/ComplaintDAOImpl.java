package dao;

import db.DatabaseConnection;
import model.Complaint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDAOImpl implements ComplaintDAO{
    public List<Complaint> getAllComplaints() {
        List<Complaint> complaints = new ArrayList<>();
        String sql = "SELECT c.*, u.username FROM complaints c " +
                "JOIN users u ON c.employee_id = u.id " +
                "ORDER BY c.created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                complaints.add(mapResultSetToComplaint(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // In production, use proper logging
        }

        return complaints;
    }

    public List<Complaint> getComplaintsByEmployee(int employeeId) {
        List<Complaint> complaints = new ArrayList<>();
        String sql = "SELECT c.*, u.username FROM complaints c " +
                "JOIN users u ON c.employee_id = u.id " +
                "WHERE c.employee_id = ? " +
                "ORDER BY c.created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                complaints.add(mapResultSetToComplaint(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return complaints;
    }

    public Complaint getComplaintById(int id) {
        String sql = "SELECT c.*, u.username FROM complaints c " +
                "JOIN users u ON c.employee_id = u.id " +
                "WHERE c.id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToComplaint(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean createComplaint(Complaint complaint) {
        String sql = "INSERT INTO complaints (title, description, priority, employee_id, status) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, complaint.getTitle());
            stmt.setString(2, complaint.getDescription());
            stmt.setString(3, complaint.getPriority());
            stmt.setInt(4, complaint.getEmployeeId());
            stmt.setString(5, complaint.getStatus());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateComplaint(Complaint complaint) {
        String sql = "UPDATE complaints SET title = ?, description = ?, priority = ?, " +
                "status = ?, admin_remarks = ?, updated_at = CURRENT_TIMESTAMP " +
                "WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, complaint.getTitle());
            stmt.setString(2, complaint.getDescription());
            stmt.setString(3, complaint.getPriority());
            stmt.setString(4, complaint.getStatus());
            stmt.setString(5, complaint.getAdminRemarks());
            stmt.setInt(6, complaint.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteComplaint(int id) {
        String sql = "DELETE FROM complaints WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Complaint mapResultSetToComplaint(ResultSet rs) throws SQLException {
        Complaint complaint = new Complaint();
        complaint.setId(rs.getInt("id"));
        complaint.setTitle(rs.getString("title"));
        complaint.setDescription(rs.getString("description"));
        complaint.setStatus(rs.getString("status"));
        complaint.setPriority(rs.getString("priority"));
        complaint.setEmployeeId(rs.getInt("employee_id"));
        complaint.setAdminRemarks(rs.getString("admin_remarks"));
        complaint.setCreatedAt(rs.getTimestamp("created_at"));
        complaint.setUpdatedAt(rs.getTimestamp("updated_at"));
        complaint.setEmployeeId(rs.getInt("id"));
        return complaint;
    }
}
