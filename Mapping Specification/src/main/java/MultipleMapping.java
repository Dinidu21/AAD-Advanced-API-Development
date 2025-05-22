import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet({"/customer", "/user"}) // Multiple URL patterns

// real world scenario

/*Your old app version sends requests to /user, but your redesigned frontend uses /customer.
Instead of refactoring both client and server logic separately,
you just use multiple mappings to point both URLs to the same processing logic.*/
public class MultipleMapping extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle the request
        response.getWriter().write("Multiple Mapping");
    }
}
