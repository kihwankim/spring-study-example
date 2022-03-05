package org.example.webmvc;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.ServletContext;
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
        ApplicationContext applicationContext = (ApplicationContext)getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        ServletContext servletContext = getServletContext();
        HelloService helloService = applicationContext.getBean("helloService", HelloService.class);
        System.out.println(servletContext.getMajorVersion());
        System.out.println(servletContext.getAttribute("helloService"));

        if (helloService == null) {
            helloService = new HelloService();
        }
        System.out.println("do Get data");
        resp.getWriter().println("<html><body>");
        resp.getWriter().println("<h1>Hello Header</h1>");
        resp.getWriter().println("input data: " + getServletContext().getAttribute("name"));
        resp.getWriter().println("<br/>bean data: " + helloService.getName());

        resp.getWriter().println("</html></body>");
    }
}
