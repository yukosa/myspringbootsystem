package com.example.system.config;

import com.example.system.bean.User;
import com.example.system.service.UserService;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获得cookie
        Cookie[] cookies = request.getCookies();
        // 没有cookie信息，则重定向到登录界面
        if (null == cookies) {
//            System.out.println("?????????????");
            request.setAttribute("msg","无权限，请登录");
            request.getRequestDispatcher("/login").forward(request,response);
//            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        // 定义cookie_username，用户的一些登录信息，例如：用户名，密码等
        String cookie_username = null;
        // 获取cookie里面的一些用户信息
        for (Cookie item : cookies) {
            System.out.println(item.getName()+" ???"+item.getValue());
//            System.out.println(item.getName()+"");
            if ("cookie_username".equals(item.getName())) {
                cookie_username = item.getValue();
                break;
            }
        }
        // 如果cookie里面没有包含用户的一些登录信息，则重定向到登录界面
        if (StringUtils.isEmpty(cookie_username)) {
//            System.out.println("HH??????????");
            request.setAttribute("msg","无权限，请登录");
            request.getRequestDispatcher("/login").forward(request,response);
//            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        // 获取HttpSession对象
        HttpSession session = request.getSession();

        // 获取我们登录后存在session中的用户信息，如果为空，表示session已经过期
        Object obj = session.getAttribute("username");

        if (null == obj) {
            // 根据用户登录账号获取数据库中的用户信息
            User user = userService.getUserById(Integer.parseInt(cookie_username));
            // 将用户保存到session中
            session.setAttribute("username", cookie_username);
            session.setAttribute("identity",user.getIdentity());
        }
        // 已经登录
        return true;

//        Object username=request.getSession().getAttribute("username");
//        if(username== null){
//            request.setAttribute("msg","无权限，请登录");
//            request.getRequestDispatcher("/login").forward(request,response);
//            return false;
//        }else{
//            return true;
//        }
    }
}
