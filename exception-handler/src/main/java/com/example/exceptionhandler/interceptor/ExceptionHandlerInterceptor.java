package com.example.exceptionhandler.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ExceptionHandlerInterceptor implements HandlerInterceptor { // handler 처리 로직 추가
    @Override // controller 접근전
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("before");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override // controller 접근 후
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override // 완료 후
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("complete");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
