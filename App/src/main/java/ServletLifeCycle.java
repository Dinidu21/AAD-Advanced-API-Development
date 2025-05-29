import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/lifecycle")
public class ServletLifeCycle extends HttpServlet {
    @Override
    public void init() throws ServletException {
        System.out.println("Servlet init");
    }

    @Override
    public void destroy() {
        System.out.println("Servlet destroy");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Servlet doGet");
    }
}
