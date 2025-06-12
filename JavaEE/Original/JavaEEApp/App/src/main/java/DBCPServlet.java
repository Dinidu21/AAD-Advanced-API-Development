import com.fasterxml.jackson.databind.ObjectMapper;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/dbcp")
public class DBCPServlet extends HttpServlet {
    BasicDataSource ds = new BasicDataSource();

    @Override
    public void init() throws ServletException {
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/eventdb");
        ds.setUsername("root");
        ds.setPassword("root123");
        ds.setInitialSize(50);
        ds.setMaxTotal(100);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Connection connection = ds.getConnection();
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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> event = mapper.readValue(req.getInputStream(), Map.class);
        try {
            Connection connection = ds.getConnection();
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
}
