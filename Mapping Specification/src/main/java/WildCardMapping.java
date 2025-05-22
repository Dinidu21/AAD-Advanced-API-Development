import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// Wildcard mapping
@WebServlet("/customer/*") // Matches any URL starting with /customer/

public class WildCardMapping extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Handle the request
        response.getWriter().write("Wildcard Mapping");
    }
}
