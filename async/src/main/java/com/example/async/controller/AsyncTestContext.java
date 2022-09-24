package com.example.async.controller;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.Executors;

@Slf4j
@WebServlet(urlPatterns = "/hello", asyncSupported = true)
public class AsyncTestContext extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("async setting");
        AsyncContext ac = request.startAsync();
        Executors.newSingleThreadExecutor().submit(() -> {
            log.info("async start");
            ac.getResponse().getWriter().println("Hello Servlet");
            ac.complete();

            return null;
        });
        log.info("main thread finished");
    }
}
