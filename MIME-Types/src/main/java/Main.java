import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@WebServlet("/mime-text")
public class Main extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("GET request received");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // read the MIME type from the request body
        String collect = new BufferedReader(new InputStreamReader(
                req.getInputStream())).lines().
                collect(Collectors.joining("\n"));

        // set the response content type to text/plain
        resp.setContentType("text/plain");

        resp.getWriter().println("MIME Type Received\n");
        resp.getWriter().write(collect);
    }
}
