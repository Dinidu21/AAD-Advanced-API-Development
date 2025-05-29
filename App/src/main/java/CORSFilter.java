import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class CORSFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:63342");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
