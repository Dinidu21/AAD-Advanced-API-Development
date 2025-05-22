import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("") // This servlet is mapped to the empty string path
public class EmptyStringMapping extends HttpServlet {
    // This servlet is mapped to the empty string path
    // It will handle requests to the root context
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle the request
        response.getWriter().write("Empty String Mapping");
    }
}
