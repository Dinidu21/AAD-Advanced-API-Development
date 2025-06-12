import com.fasterxml.jackson.databind.ObjectMapper;
import db.DBConnection;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/inbuilt")
public class InBuildDBCP extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Connection connection = dataSource.getConnection();
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

}
