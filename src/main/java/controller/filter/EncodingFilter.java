package controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by mihail on 11/23/18.
 */
@WebFilter(urlPatterns = "/*")
public class EncodingFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        servletRequest.setCharacterEncoding("utf8");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
