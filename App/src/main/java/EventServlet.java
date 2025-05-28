import com.fasterxml.jackson.databind.ObjectMapper;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/event/*")
public class EventServlet extends HttpServlet {

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DBConnection.getInstance().getConnection();

            if (pathInfo == null || pathInfo.equals("/")) {
                // Return all events
                ResultSet resultSet = connection.prepareStatement("SELECT * FROM event").executeQuery();
                List<Map<String,String>> elist = new ArrayList<>();

                while (resultSet.next()) {
                    Map<String, String> event = new HashMap<>();
                    event.put("id", resultSet.getString("id"));
                    event.put("name", resultSet.getString("name"));
                    event.put("description", resultSet.getString("description"));
                    event.put("date", resultSet.getString("date"));
                    event.put("time", resultSet.getString("time"));
                    elist.add(event);
                }

                objectMapper.writeValue(resp.getWriter(), elist);

            } else {
                // Return single event by ID
                String id = pathInfo.substring(1);
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM event WHERE id = ?");
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Map<String, String> event = new HashMap<>();
                    event.put("id", rs.getString("id"));
                    event.put("name", rs.getString("name"));
                    event.put("description", rs.getString("description"));
                    event.put("date", rs.getString("date"));
                    event.put("time", rs.getString("time"));
                    objectMapper.writeValue(resp.getWriter(), event);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("{\"error\":\"Event not found\"}");
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> event = mapper.readValue(req.getInputStream(), Map.class);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO event (id,name, description, date, time) VALUES (?, ?, ?, ?,?)"
            );
            stmt.setString(1, event.get("id"));
            stmt.setString(2, event.get("name"));
            stmt.setString(3, event.get("description"));
            stmt.setString(4, event.get("date"));
            stmt.setString(5, event.get("time"));

            int rows = stmt.executeUpdate();
            resp.setContentType("application/json");
            mapper.writeValue(resp.getWriter(), rows);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> event = mapper.readValue(req.getInputStream(), Map.class);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE event SET name = ?, description = ?, date = ?, time = ? WHERE id = ?"
            );
            stmt.setString(1, event.get("name"));
            stmt.setString(2, event.get("description"));
            stmt.setString(3, event.get("date"));
            stmt.setString(4, event.get("time"));
            stmt.setString(5, event.get("id"));

            int rows = stmt.executeUpdate();
            resp.setContentType("application/json");
            mapper.writeValue(resp.getWriter(), rows);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing event ID");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(pathInfo.substring(1));
        } catch (NumberFormatException ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid event ID");
            return;
        }

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM event WHERE id = ?");
            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Event not found");
            }
        } catch (SQLException e) {
            try {
                throw new ServletException(e);
            } catch (ServletException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}