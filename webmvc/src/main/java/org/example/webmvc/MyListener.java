package org.example.webmvc;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Context가 초기화 되었습니다");
        sce.getServletContext().setAttribute("name", "test data");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Context가 종료 되었습니다.");
    }
}
