package com.example.system.config;


import com.example.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Autowired
    private UserService userService;
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/main.html").setViewName("teaindex");
        registry.addViewController("/teaindex.html").setViewName("teaindex");
        registry.addViewController("/forms").setViewName("forms");
        registry.addViewController("/tables").setViewName("tables");
        registry.addViewController("/test").setViewName("test");
        registry.addViewController("/user/student").setViewName("stuindex.html");
//        registry.addViewController("/user/teacher").setViewName("teacher/teaindex.html");
        registry.addViewController("/user/teacher/forms").setViewName("teacher/forms.html");
        registry.addViewController("/user/manage").setViewName("manage/manindex.html");
        registry.addViewController("/user/manage").setViewName("manage/manindex.html");

    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor(userService)).addPathPatterns("/**").excludePathPatterns("/user/login","/login","/","/css/*","/js/*","/assets/**","/img/**");
    }
}
