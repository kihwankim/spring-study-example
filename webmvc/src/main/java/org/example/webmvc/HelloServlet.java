package org.example.webmvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("init");
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("destory");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("do Get data");
        resp.getWriter().println("<html><body>");
        resp.getWriter().println("<h1>Hello Header</h1>");
        resp.getWriter().println("input data " + getServletContext().getAttribute("name"));
        resp.getWriter().println("</html></body>");
    }
}
