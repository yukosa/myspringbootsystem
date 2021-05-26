package com.example.system.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object username=request.getSession().getAttribute("username");
        if(username== null){
            request.setAttribute("msg","无权限，请登录");
            request.getRequestDispatcher("/login").forward(request,response);
            return false;
        }else{
            return true;
        }
    }
}
