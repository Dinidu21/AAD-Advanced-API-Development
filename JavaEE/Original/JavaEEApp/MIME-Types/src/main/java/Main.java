import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/mime-text")
@MultipartConfig
public class Main extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("GET request received");
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // read the MIME type from the request body
//        String collect = new BufferedReader(new InputStreamReader(
//                req.getInputStream())).lines().
//                collect(Collectors.joining("\n"));
//
//        // set the response content type to text/plain
//        resp.setContentType("text/plain");
//
//        resp.getWriter().println("MIME Type Received\n");
//        resp.getWriter().write(collect);
//    }

/*    // read x-www-form-urlencoded data from the request body
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String add = req.getParameter("address");
        resp.setContentType("text/plain");
        resp.getWriter().write("Name: " + name + "\nAddress: " + add);
    }*/

/*    // read multipart/form-data from the request body
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = req.getParameter("text");
        Part file = req.getPart("file");
        String fileName = file.getSubmittedFileName();

        // set the response content type to text/plain
        resp.setContentType("text/plain");
        resp.getWriter().write("Text :" + text+"\n");
        resp.getWriter().write("File Name: " + fileName);
    }*/

    //read JSON data from the request body

/*    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(req.getReader());

        String name = node.get("name").asText();
        String address = node.get("address").asText();

        // set the response content type to text/plain
        resp.setContentType("text/plain");
        resp.getWriter().write("Name: " + name + "\nAddress: " + address);
    }*/

    // read JSON array from the request body
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<JsonNode> users = mapper.readValue(req.getReader(),
                new TypeReference<List<JsonNode>>() {
                });
        for(JsonNode user : users) {
            String name = user.get("name").asText();
            String address = user.get("address").asText();

            // set the response content type to text/plain
            resp.setContentType("text/plain");
            resp.getWriter().write("\nName: " + name + "\nAddress: " + address);
        }

    }
}