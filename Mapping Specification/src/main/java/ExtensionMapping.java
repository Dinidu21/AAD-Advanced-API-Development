import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("*.txt")
public class ExtensionMapping extends HttpServlet {

    // Handle the request
    // This method will be called for any request with a .txt extension
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Handle the request
        response.getWriter().write("Extension Mapping");
    }
}

